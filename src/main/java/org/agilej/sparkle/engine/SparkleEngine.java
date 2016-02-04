package org.agilej.sparkle.engine;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.ApplicationController;
import org.agilej.sparkle.Env;
import org.agilej.sparkle.HTTPMethod;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.async.DeferredResult;
import org.agilej.sparkle.config.Application;
import org.agilej.sparkle.core.ActionMethod;
import org.agilej.sparkle.core.ConfigResult;
import org.agilej.sparkle.core.ControllerFactory;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.agilej.sparkle.core.dev.ExceptionHandler;
import org.agilej.sparkle.core.dev.RouteNotFoundHandler;
import org.agilej.sparkle.core.request.*;
import org.agilej.sparkle.core.support.SimpleControllerFactoryResolver;
import org.agilej.sparkle.core.view.SimpleViewRenderResolver;
import org.agilej.sparkle.core.view.ViewRender;
import org.agilej.sparkle.core.view.ViewRenderManager;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.ext.EnvSpecific;
import org.agilej.sparkle.ext.VendorViewRenderProvider;
import org.agilej.sparkle.http.HTTPStatusCode;
import org.agilej.sparkle.interceptor.Interceptor;
import org.agilej.sparkle.route.RouteModule;

import org.agilej.fava.FArrayList;
import org.agilej.fava.FList;
import org.agilej.sparkle.core.method.*;
import org.agilej.sparkle.core.route.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class SparkleEngine implements ViewRenderingPhaseExecutor, DeferredResult.DeferredResultHandler{

    private FList<Interceptor> interceptors;
    private RouterImpl router;
    private ConfigImpl config;
    private ControllerFactory controllerFactory;
    private RouteBuilderResolver routeBuilderResovler;
    private ControllerClassResolver controllerClassResolver;
    private ActionMethodResolver actionMethodResolver;
    private ViewRenderResolver viewRenderResolver;
    private EnvSpecific envSpecific;
    private ArgumentResolverManager argumentResolverManager;

    private final static Logger logger = LoggerFactory.getLogger(SparkleEngine.class);
    
    public SparkleEngine(EnvSpecific es){

        Stopwatch stopwatch = Stopwatch.createStarted();
        logger.info("Start initializing sparkle framework.");


        this.envSpecific             = es;
        this.config                  = new ConfigImpl();
        this.interceptors            = new FArrayList<Interceptor>();

        this.router                  = new RouterImpl();
        this.routeBuilderResovler    = new SimpleRouteBuilderResolver(this.router);

        this.controllerClassResolver = new ControllersHolder();
        this.controllerFactory       = new SimpleControllerFactoryResolver().get(this.config);
        this.actionMethodResolver    = new ActionMethodResolver();

        this.argumentResolverManager =  this.envSpecific.getArgumentResolverManager();

        this.startup();

        stopwatch.stop();
        logger.info("Sparkle framework start succeed within {} ms \n", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    protected void startup() {
        Application application = scanApplication();
        if(application != null){
            logger.info("Found customized Application Configuration : {}", application.getClass().getSimpleName());
            application.config(config);
        }else{
            logger.info("Could not find any ApplicationConfig, Sparkle will use the default configuration");
        }
        initEngineWithConfig(config);
    }
 
    private void initEngineWithConfig(ConfigResult config) {
        initViewRenders(config);
        initControllers(config);
        installRouter();
        initInterceptors(config);
        initSessionStore(config);
        initLocaleResolver(config);
    }

    private void initLocaleResolver(ConfigResult config) {
        LocaleResolver localeResolver = new LocaleResolverResolver(config).resolve();
        LocaleResolverHolder.set(localeResolver);
        logger.info("Sparkle's locale resolver is configured to: {}", localeResolver.getClass().getSimpleName());
    }

    private void initSessionStore(ConfigResult config) {
        SessionStore sessionStore = new SessionStoreResolver(config).resolve();
        SessionStoreHolder.set(sessionStore);
        logger.info("Sparkle's session store is configured to: {}", sessionStore.getClass().getSimpleName());
    }

    private void initInterceptors(ConfigResult config) {
        this.interceptors.addAll(config.getInterceptors());
    }

    private void initViewRenders(ConfigResult config) {
        ViewRenderManager viewRenderManager = new ViewRenderManager();
        viewRenderManager.registerAppScopedViewRender(config.getCustomizedViewRenders());

        VendorViewRenderProvider vendorViewRenderProvider = this.envSpecific.vendorViewRenderProvider();
        if (vendorViewRenderProvider != null){
            viewRenderManager.registerVendorViewRenders(vendorViewRenderProvider.vendorViewRenders());
        }

        this.viewRenderResolver = new SimpleViewRenderResolver(viewRenderManager.getAllOrderedViewRenders());
    }

    private void initControllers(ConfigResult config) {
        //TODO how to deal with multi controller packages
        Map<String, Class<?>> scannedControllers = new ControllerScanner().scanControllers(this.config.getBasePackage());
        this.controllerClassResolver.registeControllers(scannedControllers, true);
    }

    private boolean executePreInterceptor(InterceptorExecutionChain interceptorExecutionChain,  WebRequest webRequest){
        return interceptorExecutionChain.doPreHandle(webRequest);
    }

    private void executeAfterInterceptor(InterceptorExecutionChain interceptorExecutionChain,  WebRequest webRequest){
        interceptorExecutionChain.doAfterHandle(webRequest);
    }

    private Object getControllerInstanceForRoute(RouteInfo rd){
        String controllerName = rd.getControllerName();
        final Class<?> controllerClass = this.controllerClassResolver.getControllerClass(controllerName);
        final Object controller  = this.controllerFactory.get(controllerName, controllerClass);
        if(controller == null){
            logger.error("Can't get controller instance with name : {} and class : {}", controllerName, controllerClass);
            throw new SparkleException("Can't get controller instance with name : %s and class : %s", controllerName, controllerClass);
        }
        return controller;
    }

    public void doService(final WebRequest webRequest, HTTPMethod method){
        logger.info("Processing request : {} {}", webRequest.getMethod(), webRequest.getPath());

        InterceptorExecutionChain ic = new InterceptorExecutionChain(this.interceptors);
        WebRequestExecutionContext ctx = new WebRequestExecutionContext(webRequest, ic);

        Stopwatch stopwatch = ctx.stopwatch().start();

        RouteInfo rd = this.routeBuilderResovler.match(webRequest);

        if(rd == null){
            logger.info("Could not find route for request : [{} {}] \n", webRequest.getMethod(), webRequest.getPath());
            webRequest.getWebResponse().setStatus(HTTPStatusCode.NOT_FOUND);
            if (Env.isDev()){
                new RouteNotFoundHandler(this.router).handle(webRequest);
            }
//            executeAfterInterceptor(ic, webRequest);
            return;
        }

        //execute interceptor
        boolean interceptorPassed = executePreInterceptor(ic, webRequest);
        if(!interceptorPassed){
            logger.info("Interceptor execute failed, request processing ignored.");
            executeAfterInterceptor(ic, webRequest);
            return;
        }

        setPathVariablesToRequestAttribute(webRequest, rd);  //extract path variables

        if (rd.isFunctionRoute()){
            logger.debug("Execute action for functional route : {}", rd.getRouteFunction());
            Object result = rd.getRouteFunction().apply(webRequest);
            processViewAndAfterInteceptors(result, ctx, true, null, null);
            return ;
        }

        //create controller object
        final Object controller = getControllerInstanceForRoute(rd);

        presetControllerIfNeed(webRequest, controller);

        final ActionMethod actionMethod = this.actionMethodResolver.find(controller.getClass(), rd.getActionName());

        Object result = new ControllerExecutor(this.argumentResolverManager).execute(actionMethod, controller, webRequest);
        if(result instanceof Callable){
            logger.debug("action result is Callable, will execute asynchronously");
            startAsyncProcess((Callable<Object>)result, ctx, controller, actionMethod);
            return;
        }
        if (result instanceof DeferredResult) {
            webRequest.startAsync();
            ((DeferredResult) result).setResultHandler(this);
        }
        boolean isResponseProcessedManually = isResponseProcessedManually(actionMethod);
        processViewAndAfterInteceptors(result, ctx, !isResponseProcessedManually, controller, actionMethod);
    }

    @Override
    public void handleResult(Object result) {
//        processViewAndAfterInteceptors(result, ctx, !isResponseProcessedManually(actionMethod), controller, actionMethod);
//        ctx.webRequest().completeAsync();
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public void doRenderViewPhase(WebRequest webRequest, Object controller, ActionMethod actionMethod, Object result) {
        if (controller != null && actionMethod != null){
            logger.debug("Try to render view for {}#{} with result type : {}",
                    controller.getClass().getSimpleName(), actionMethod.actionName(), result.getClass().getSimpleName());
        } else {  // it's a functional route
            logger.debug("Try to render view for functional route with result type : {}", result.getClass().getSimpleName());
        }

        ViewRender viewRender = this.viewRenderResolver.resolveViewRender(actionMethod, result);
        if(viewRender != null){
            try {
                viewRender.renderView(result, controller, webRequest);
            } catch (IOException e) {
                throw new SparkleException(e.getMessage());
            }
        } else {
            logger.error("Could not find any view render for request {}, controller#action is {}#{}, result type is {}",
                    webRequest, controller.getClass().getSimpleName(), actionMethod.actionName(), result.getClass().getSimpleName());
        }
    }

    // execute after real action result got
    private void processViewAndAfterInteceptors(Object result, WebRequestExecutionContext ctx,
                                                boolean needRender, Object controller, ActionMethod actionMethod){
        Stopwatch stopwatch = ctx.stopwatch().stop();
        long actionTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        stopwatch.reset().start();

        if (needRender){
            doRenderViewPhase(ctx.webRequest(), controller, actionMethod, result);
        } else {
            logger.debug("Http servlet response has been proceed manually, ignore view rendering.");
        }

        InterceptorExecutionChain ic = ctx.interceptorExecutionChain();
        if(ic.isAllPassed()){
            ic.doAfterHandle(ctx.webRequest());
        }
        long viewTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        logger.info("Completed request within {} ms (Action: {} ms | View: {} ms)\n",
                new Object[]{viewTime + actionTime, actionTime, viewTime });
    }


    private boolean isResponseProcessedManually(ActionMethod actionMethod) {
        return this.envSpecific.getLifeCycleManager().isResponseProcessedManually(actionMethod);
    }

    private ExecutorService es = Executors.newCachedThreadPool();//.newFixedThreadPool(100);


    private void startAsyncProcess(final Callable<Object> callable, final WebRequestExecutionContext ctx,
                                   final Object controller, final ActionMethod actionMethod){
        
        ctx.webRequest().startAsync();
        CompletableFuture
                .supplyAsync(() -> {
                    try {
                        logger.info("Execute action asynchronously for {}#{}",
                                controller.getClass().getSimpleName(), actionMethod.actionName());
                        return callable.call();
                    } catch (Exception e) {
                        logger.error("Error occurred while executing asynchronously action : {}", e);
                        throw new RuntimeException("Execute async action failed", e);
                    }
                }, es).whenComplete((result,  ex) -> {
                    if (ex == null) { // means no error
                        processViewAndAfterInteceptors(result, ctx, !isResponseProcessedManually(actionMethod), controller, actionMethod);
                        ctx.webRequest().completeAsync();
                    } else {
                        logger.error("Error occurred while executing asynchronously action : {}", ex);
                        ctx.webRequest().getWebResponse().setStatus(500);
                        if (Env.isDev()){
                            new ExceptionHandler(ex.getCause()).handle(ctx.webRequest());
                        }
                        ctx.webRequest().completeAsync();
                    }
                });  //TODO need deal with view render exception
    }

    private void installRouter() {
        String routePackage = "";
        List<RouteModule> routeModules = new RouteModuleScanner().scanRouteModule(routePackage);
        this.router.install(routeModules);
        for(RouteBuilder rb : this.router.getRegisteredRouteBuilders()){
            logger.info("Registered route : {}", rb.toString());
        }
    }

    private Application scanApplication() {
        Class<?> clz = new ApplicationConfigScanner().scan("");
        return clz != null ? (Application) ReflectionUtil.initialize(clz) : null;
    }

    private void presetControllerIfNeed(WebRequest webRequest, Object controller) {
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(webRequest);
            ((ApplicationController)controller).setResponse(webRequest.getWebResponse());
        }
    }

    private void setPathVariablesToRequestAttribute(WebRequest webRequest, RouteInfo rd) {
//        Map<String, String> pathVariables = PathVariableDetector.extractPathVariables(rd, webRequest);
        Map<String, String> pathVariables = rd.pathVariables(webRequest.getPath());
        webRequest.setAttribute(WebRequest.REQ_ATTR_FOR_PATH_VARIABLES, pathVariables);
    }


    public void shutdown(){
        es.shutdown();
    }

}
