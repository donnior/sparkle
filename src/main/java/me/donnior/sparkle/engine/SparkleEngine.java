package me.donnior.sparkle.engine;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.config.Application;
import me.donnior.sparkle.core.ActionMethod;
import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.core.argument.ArgumentResolverManager;
import me.donnior.sparkle.core.method.*;
import me.donnior.sparkle.core.request.SessionStore;
import me.donnior.sparkle.core.request.SessionStoreHolder;
import me.donnior.sparkle.core.request.SessionStoreResolver;
import me.donnior.sparkle.core.route.*;
import me.donnior.sparkle.core.support.SimpleControllerFactoryResolver;
import me.donnior.sparkle.core.view.SimpleViewRenderResolver;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.core.view.ViewRenderResolver;
import me.donnior.sparkle.exception.SparkleException;
import me.donnior.sparkle.ext.EnvSpecific;
import me.donnior.sparkle.http.HTTPStatusCode;
import me.donnior.sparkle.interceptor.Interceptor;
import me.donnior.sparkle.route.RouteModule;

import org.agilej.fava.FArrayList;
import org.agilej.fava.FList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class SparkleEngine implements ViewRenderingPhaseExecutor{

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

        this.controllerClassResolver = ControllersHolder.getInstance();
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
            application.config(config);
        }else{
            logger.info("Could not find any ApplicationConfig, Sparkle will use the default configuration");
        }
        initEngineWithConfig(config);
    }
 
    private void initEngineWithConfig(ConfigResult config) {
        initViewRenders(config);
        initControllers(config);
//        initControllerFactory(config);
        installRouter();
        initInterceptors(config);
        initSessionStore(config);
    }

    private void initSessionStore(ConfigResult config) {
        SessionStore sessionStore = new SessionStoreResolver().resolve(config);
        SessionStoreHolder.set(sessionStore);
        logger.info("Sparkle's session store is configured to: {}", sessionStore.getClass().getSimpleName());
    }

    private void initInterceptors(ConfigResult config) {
        this.interceptors.addAll(config.getInterceptors());
    }

    private void initViewRenders(ConfigResult config) {

        this.envSpecific.getViewRendersManager().registerAppScopedViewRender(config.getCustomizedViewRenders());
//        List<ViewRender> viewRenders =
//            this.envSpecific.getViewRendersManager().resolveRegisteredViewRenders(config.getCustomizedViewRenders());
        this.viewRenderResolver =
                new SimpleViewRenderResolver(this.envSpecific.getViewRendersManager().getAllOrderedViewRenders());
    }

    //TODO how to make the controller factory can be customized, for example let user use an
    //spring container as this factory? Maybe introduce a ControllerFactoryResolver based on ConfigAware is better?
    private void initControllerFactory(ConfigResult config) {
        if(config.getControllerFactory() != null){
            this.controllerFactory = config.getControllerFactory();
            return;
        }
        if(config.getControllerFactoryClass() != null){
            this.controllerFactory = (ControllerFactory)ReflectionUtil.initialize(config.getControllerFactoryClass());
            return;
        }
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
            throw new SparkleException("Can't get controller instance with name : " + controllerName + " and class : "+ controllerClass);
        }
        return controller;
    }

    public void doService(final WebRequest webRequest, HTTPMethod method){
        logger.info("Processing request : {} {}", webRequest.getMethod(), webRequest.getPath());

        InterceptorExecutionChain ic = new InterceptorExecutionChain(this.interceptors);
        WebRequestExecutionContext ctx = new WebRequestExecutionContext(webRequest, ic);

        Stopwatch stopwatch = ctx.stopwatch().start();

        //execute interceptor

        boolean interceptorPassed = executePreInterceptor(ic, webRequest);
        if(!interceptorPassed){
            logger.info("Interceptor execute failed, request processing ignored.");
            executeAfterInterceptor(ic, webRequest);
            return;
        }

        RouteInfo rd = this.routeBuilderResovler.match(webRequest);

        if(rd == null){
            logger.info("Could not find route for request : '{}' \n", webRequest);
            webRequest.getWebResponse().setStatus(HTTPStatusCode.NOT_FOUND);
            return;
        }

        setPathVariablesToRequestAttribute(webRequest, rd);  //extract path variables

        if (rd.isFunctionRoute()){
            logger.debug("Execute action for functional route : {}", rd.getRouteFunction());
            Object result = rd.getRouteFunction().apply(webRequest);
            triggerViewRender(result, ctx, true, null, null);
            return ;
        }

        //create controller object
        final Object controller = getControllerInstanceForRoute(rd);

        presetControllerIfNeed(webRequest, controller);

        final ActionMethod actionMethod = this.actionMethodResolver.find(controller.getClass(), rd.getActionName());

        Object result = new ControllerExecutor(this.argumentResolverManager).execute(actionMethod, controller, webRequest);
        if(result instanceof Callable){
            startAsyncProcess((Callable<Object>)result, ctx, controller, actionMethod);
            return;
        }

        boolean isResponseProcessedManually = isResponseProcessedManually(actionMethod);
        triggerViewRender(result, ctx, !isResponseProcessedManually, controller, actionMethod);
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
    private void triggerViewRender(Object result, WebRequestExecutionContext ctx,
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
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    logger.info("Execute action asynchronously for {}#{}",
//                              controller.getClass().getSimpleName(), actionMethod.actionName());
//                    Object result = callable.call();
////                    doRenderViewPhase(ctx.webRequest(), controller, actionMethod, result);
//
//                    //TODO trigger view render with result
//                    triggerViewRender(result, ctx, !isResponseProcessedManually(actionMethod), controller, actionMethod);
//
//                    ctx.webRequest().completeAsync();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//        es.submit(r);

        //use CompletableFuture,  should check CPU use.
        CompletableFuture
                .supplyAsync(() -> {
                    try {
                        logger.info("Execute action asynchronously for {}#{}",
                                controller.getClass().getSimpleName(), actionMethod.actionName());
                        return callable.call();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }, es).whenComplete((result,  ex) -> {
                    if (ex == null) { // means no error
                        triggerViewRender(result, ctx, !isResponseProcessedManually(actionMethod), controller, actionMethod);
                        ctx.webRequest().completeAsync();
                    } else {
                        //TODO deal with action call error
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
