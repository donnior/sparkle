package org.agilej.sparkle.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Consumer;

import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.ApplicationController;
import org.agilej.sparkle.Env;
import org.agilej.sparkle.HTTPMethod;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.async.DeferredResult;
import org.agilej.sparkle.config.Application;
import org.agilej.sparkle.core.action.ActionMethod;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.action.ControllerFactory;
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

import org.agilej.sparkle.core.method.*;
import org.agilej.sparkle.core.route.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class SparkleEngine implements ViewRenderingPhaseExecutor{

    private List<Interceptor> interceptors;

    private RouterImpl router;
    private ConfigImpl config;
    private ControllerFactory controllerFactory;
    private RouteBuilderResolver routeBuilderResolver;
    private ControllerClassResolver controllerClassResolver;
    private ActionMethodResolver actionMethodResolver;
    private ViewRenderResolver viewRenderResolver;
    private ArgumentResolverManager argumentResolverManager;

    private EnvSpecific envSpecific;

    private ExecutorService asyncTaskExecutorService = Executors.newCachedThreadPool(); //.newFixedThreadPool(100);

    private final static Logger logger = LoggerFactory.getLogger(SparkleEngine.class);
    
    public SparkleEngine(EnvSpecific envSpecific){

        logger.info("Start initializing Sparkle framework.");

        Stopwatch stopwatch = Stopwatch.createStarted();

        this.envSpecific             = envSpecific;
        this.config                  = new ConfigImpl();
        this.interceptors            = new ArrayList<Interceptor>();

        this.router                  = new RouterImpl();
        this.routeBuilderResolver    = new SimpleRouteBuilderResolver(this.router);

        this.controllerClassResolver = new ControllersHolder();
        this.controllerFactory       = new SimpleControllerFactoryResolver().get(this.config);
        this.actionMethodResolver    = new ActionMethodResolver();

        this.argumentResolverManager = this.envSpecific.getArgumentResolverManager();

        this.startup();

        stopwatch.stop();
        logger.info("Sparkle framework start succeed within {} ms \n", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    protected void startup() {
        Application application = scanApplication();
        if(application != null){
            logger.info("Found customized application config : {}", application.getClass().getSimpleName());
            application.config(config);
        }else{
            logger.info("Could not find any application config, Sparkle will use the default configuration");
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
        //TODO remove ViewRenderManager, inject Config to SimpleViewRenderResolver directly and resolve all renders
        this.viewRenderResolver = new SimpleViewRenderResolver(viewRenderManager.getAllOrderedViewRenders());
    }

    private void initControllers(ConfigResult config) {
        //TODO how to deal with multi controller packages
        Map<String, Class<?>> scannedControllers = new ControllerScanner().scanControllers(this.config.getBasePackage());
        this.controllerClassResolver.registerControllers(scannedControllers, true);
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
            throw new SparkleException("Can't get controller instance with name : %s and class : %s",
                    controllerName, controllerClass);
        }
        return controller;
    }

    public void doService(final WebRequest webRequest, HTTPMethod method){
        logger.info("Processing request : {} {}", webRequest.getMethod(), webRequest.getPath());

        InterceptorExecutionChain ic = new InterceptorExecutionChain(this.interceptors);
        WebRequestExecutionContext ctx = new WebRequestExecutionContext(webRequest, ic);

        Stopwatch stopwatch = ctx.stopwatch().start();

        RouteInfo rd = this.routeBuilderResolver.match(webRequest);

        if(rd == null){
            logger.info("Could not find route for request : [{} {}] \n", webRequest.getMethod(), webRequest.getPath());
            webRequest.getWebResponse().setStatus(HTTPStatusCode.NOT_FOUND);
            if (Env.isDev()){
                new RouteNotFoundHandler(this.router).handle(webRequest);
            }
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

        Object tempResult = null;
        Object controller = null;
        ActionMethod actionMethod = null;

        if (rd.isFunctionRoute()){
            logger.debug("Execute action for functional route : {}", rd.getRouteFunction());
            tempResult = rd.getRouteFunction().apply(webRequest);
        } else {
            controller = getControllerInstanceForRoute(rd);
            presetControllerIfNeed(webRequest, controller);
            actionMethod = this.actionMethodResolver.find(controller.getClass(), rd.getActionName());
            tempResult = new ControllerExecutor(this.argumentResolverManager).execute(actionMethod, controller, webRequest);
        }

        final Object _controller = controller;
        final ActionMethod _actionMethod = actionMethod;

        if(tempResult instanceof Callable){                            //self-organized async processing
            logger.debug("Execute action asynchronously for {}#{}",
                    controller.getClass().getSimpleName(), actionMethod.actionName());

            startAsyncProcess((Callable<Object>)tempResult, ctx, result->{
                processViewResult(result,ctx,true,_controller, _actionMethod);
                executeAfterInterceptor(ctx.interceptorExecutionChain(), webRequest);
            }, error -> {
                logger.error("Error occurred while executing asynchronously action : {}", error);

                ctx.webRequest().getWebResponse().setStatus(500);
                if (Env.isDev()){
                    new ExceptionHandler(error).handle(ctx.webRequest());
                }
            });
            return;
        }

        if (tempResult instanceof DeferredResult) {                     //delegated async processing
            webRequest.startAsync();
            ((DeferredResult) tempResult).setResultHandler(new DeferredResult.DeferredResultHandler() {
                @Override
                public void handleResult(Object result) {
                    processViewResult(result,ctx,true,_controller, _actionMethod);
                    webRequest.completeAsync();
                    executeAfterInterceptor(ctx.interceptorExecutionChain(), webRequest);
                }
            });
        }

        boolean isResponseProcessedManually = isResponseProcessedManually(actionMethod);
        if (isResponseProcessedManually) {
            //TODO
        } else {
            processViewResult(tempResult, ctx, true, controller, actionMethod);
            executeAfterInterceptor(ctx.interceptorExecutionChain(), webRequest);
        }
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
            logger.error("Could not find any view render for request {}, result type is {}",
                    webRequest, result.getClass().getSimpleName());
        }
    }

    private void processViewResult(Object result, WebRequestExecutionContext ctx,
                                                 boolean needRender, Object controller, ActionMethod actionMethod){
        Stopwatch stopwatch = ctx.stopwatch().stop();
        long actionTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        stopwatch.reset().start();

        if (needRender){
            doRenderViewPhase(ctx.webRequest(), controller, actionMethod, result);
        } else {
            logger.debug("Http servlet response has been proceed manually, ignore view rendering.");
        }

        long viewTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        logger.info("Completed request within {} ms (Action: {} ms | View: {} ms)\n",
                new Object[]{viewTime + actionTime, actionTime, viewTime });
    }


    private boolean isResponseProcessedManually(ActionMethod actionMethod) {
        return this.envSpecific.getLifeCycleManager().isResponseProcessedManually(actionMethod);
    }




    private void startAsyncProcess(final Callable<Object> callable,
                                   final WebRequestExecutionContext ctx,
                                   final Consumer succeedConsumer,
                                   final Consumer<Throwable> errorConsumer){

        ctx.webRequest().startAsync();
        CompletableFuture
                .supplyAsync(() -> {
                        try {
                            return callable.call();
                        } catch (Exception e) {
                            throw new RuntimeException("Execute async action failed with : ", e);
                        }
                    }, asyncTaskExecutorService)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        succeedConsumer.accept(result);
                    } else {
                        errorConsumer.accept(ex.getCause());
                    }
                    ctx.webRequest().completeAsync();
                });  //TODO deal the order completeAsync and execute after filters
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
        Map<String, String> pathVariables = rd.pathVariables(webRequest.getPath());
        webRequest.setAttribute(WebRequest.REQ_ATTR_FOR_PATH_VARIABLES, pathVariables);
    }

    public void shutdown(){
        asyncTaskExecutorService.shutdown();
    }

}
