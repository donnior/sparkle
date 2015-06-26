package me.donnior.sparkle.engine;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.function.Function;

import me.donnior.fava.FArrayList;
import me.donnior.fava.FList;
import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.WebResponse;
import me.donnior.sparkle.config.Application;
import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.resolver.*;
import me.donnior.sparkle.core.route.RouteBuilder;
import me.donnior.sparkle.core.route.RouteBuilderResolver;
import me.donnior.sparkle.core.route.RouterImpl;
import me.donnior.sparkle.core.route.SimpleRouteBuilderResolver;
import me.donnior.sparkle.core.view.SimpleViewRenderResolver;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.ext.EnvSpecific;
import me.donnior.sparkle.http.HTTPStatusCode;
import me.donnior.sparkle.interceptor.Interceptor;
import me.donnior.sparkle.route.RouteModule;

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
    private ActionMethodDefinitionFinder actionMethodResolver;
    private ViewRenderResolver viewRenderResolver;
    private EnvSpecific envSpecific;
    private ArgumentResolverManager argumentResolverManager;

    private final static Logger logger = LoggerFactory.getLogger(SparkleEngine.class);
    
    public SparkleEngine(EnvSpecific es){
        this.envSpecific             = es;
        this.config                  = new ConfigImpl();
        this.interceptors            = new FArrayList<Interceptor>();
        this.router                  = RouterImpl.getInstance();
        this.controllerFactory       = new GuiceControllerFactory();
        this.routeBuilderResovler    = new SimpleRouteBuilderResolver(this.router);
        this.actionMethodResolver    = new ActionMethodDefinitionFinder();
        this.controllerClassResolver = ControllersHolder.getInstance();
        this.argumentResolverManager =  this.envSpecific.getArgumentResolverManager();
        this.startup();
    }

    protected void startup() {
        Stopwatch stopwatch = Stopwatch.createStarted();
        logger.info("Start initializing sparkle framework.");

        Application application = scanApplication();
        if(application != null){
            application.config(config);
        }else{
            logger.debug("Could not find any ApplicationConfig, Sparkle will use the default configuration");
        }
        initEngineWithConfig(config);

        stopwatch.stop();
        logger.info("Sparkle framework start succeed within {} ms \n", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

 
    private void initEngineWithConfig(ConfigResult config) {

        initViewRenders(config);
        
        initControllers(config);
        
        initControllerFactory(config);
        
        installRouter();
        
        initInterceptors(config);
        
    }

    private void initInterceptors(ConfigResult config) {
        this.interceptors.addAll(config.getInterceptors());
    }

    private void initViewRenders(ConfigResult config) {
        List<ViewRender> viewRenders = 
            this.envSpecific.getViewRendersManager().resolveRegisteredViewRenders(config.getCustomizedViewRenders());
        this.viewRenderResolver = new SimpleViewRenderResolver(viewRenders);
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

    private Object getControllerInstanceForRoute(RouteBuilder rd){
        String controllerName = rd.getControllerName();
        final Class<?> controllerClass = this.controllerClassResolver.getControllerClass(controllerName);
        final Object controller  = this.controllerFactory.get(controllerName, controllerClass);
        if(controller == null){
            logger.error("Can't get controller instance with name : {} and class : {}", controllerName, controllerClass);
            throw new RuntimeException("Can't get controller instance with name : " + controllerName + " and class : "+ controllerClass);
        }
        return controller;
    }

    // execute after real action result got
    private void triggerViewRender(Object result, WebRequestExecutionContext ctx, InterceptorExecutionChain ic){
        Stopwatch stopwatch = ctx.stopwatch().stop();
        long actionTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        stopwatch.reset().start();

        doRenderViewPhase(ctx.webRequest(), null, null, result);

        if(ic.isAllPassed()){
            ic.doAfterHandle(ctx.webRequest());
        }
        long viewTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        logger.info("Completed request (route function) within {} ms (Action: {} ms | View: {} ms)\n", new Object[]{viewTime + actionTime, actionTime, viewTime });
    }

    public void doService(final WebRequest webRequest, HTTPMethod method){
        logger.info("Processing request : {} {}", webRequest.getMethod(), webRequest.getPath());
        WebRequestExecutionContext ctx = new WebRequestExecutionContext(webRequest);
        Stopwatch stopwatch = ctx.stopwatch().start();

        //execute interceptor
        InterceptorExecutionChain ic = new InterceptorExecutionChain(this.interceptors);
        boolean interceptorPassed = executePreInterceptor(ic, webRequest);
        if(!interceptorPassed){
            logger.info("Interceptor execute failed");
            executeAfterInterceptor(ic, webRequest);
            return;
        }
        //find router, or will render 404
        RouteBuilder rd = this.routeBuilderResovler.match(webRequest);

        if(rd == null){
            logger.info("Could not find route for request : '{}' \n", webRequest);
            WebResponse response = webRequest.getWebResponse();
            response.setStatus(HTTPStatusCode.NOT_FOUND);
            return;
        }


        if (rd.isFunctionRoute()){
            Object result = rd.getRouteFunction().apply(webRequest);
            triggerViewRender(result, ctx, ic);
//            stopwatch.stop();
//            long actionTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
//            stopwatch.reset().start();
//            doRenderViewPhase(webRequest, null, null, result);
//            if(interceptorPassed){
//                ic.doAfterHandle(webRequest);
//            }
//            long viewTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
//            logger.info("Completed request (route function) within {} ms (Action: {} ms | View: {} ms)\n", new Object[]{viewTime + actionTime, actionTime, viewTime });

            return ;
        }

        //create controller object
        final Object controller = getControllerInstanceForRoute(rd);

        presetControllerIfNeed(webRequest, controller);
        setPathVariablesToRequestAttribute(webRequest, rd);  //extract path variables

        final ActionMethodDefinition adf = this.actionMethodResolver.find(controller.getClass(), rd.getActionName());

        Object result = new ControllerExecutor(this.argumentResolverManager).execute(adf, controller, webRequest);
        if(result instanceof Callable){
            startAsyncProcess((Callable<Object>)result, webRequest, controller, adf);
            return;
        }

        stopwatch.stop();
        long actionTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        stopwatch.reset().start();

        boolean isResponseProcessedMannually = isResponseProcessedManually(adf);
        if(!isResponseProcessedMannually){
            doRenderViewPhase(webRequest, controller, adf, result);
        } else {
            logger.debug("Http servlet response has been proceed manually, ignore view rendering.");
        }
        
        //TODO maybe should put this in a try-catch-finally clause? We should cleanup interceptors even action execution throws exception
        if(ic.isAllPassed()){
            ic.doAfterHandle(webRequest);
        }
        
        long viewTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        logger.info("Completed request within {} ms (Action: {} ms | View: {} ms)\n", new Object[]{viewTime + actionTime, actionTime, viewTime });
    }

    private void presetControllerIfNeed(WebRequest webRequest, Object controller) {
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(webRequest);
            ((ApplicationController)controller).setResponse(webRequest.getWebResponse());
        }
    }

    private void setPathVariablesToRequestAttribute(WebRequest webRequest, RouteBuilder rd) {
        Map<String, String> pathVariables = PathVariableDetector.extractPathVariables(rd, webRequest);
        webRequest.setAttribute(WebRequest.REQ_ATTR_FOR_PATH_VARIABLES, pathVariables);
    }

    @Override
    public void doRenderViewPhase(WebRequest webRequest, Object controller, ActionMethodDefinition adf, Object result) {

        //TODO how to resolve view ? not just json or jsp, consider jsp, freemarker, vocility.
        //Reference springmvc's viewResolver
//        logger.debug("Render view for {}#{} with result type {}", controller.getClass().getSimpleName(), adf.actionName(), result.getClass().getSimpleName());
        ViewRender viewRender = this.viewRenderResolver.resolveViewRender(adf, result);
        if(viewRender != null){
            try {
                viewRender.renderView(result, controller, webRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * if one action method has a HttpServletResponse argument, 
     * then suppose user want process response manually, will ignore the view rendering phase.
     * @param adf
     * @return
     */
    private boolean isResponseProcessedManually(ActionMethodDefinition adf) {
        return this.envSpecific.getLifeCycleManager().isResponseProcessedManually(adf);
    }

    private ExecutorService es = Executors.newCachedThreadPool();//.newFixedThreadPool(100);
    
    private void startAsyncProcess(final Callable<Object> callable, final WebRequest webRequest, final Object controller, final ActionMethodDefinition adf){
        webRequest.startAsync();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    logger.info("Execute action asynchronously for {}#{}", controller.getClass().getSimpleName(), adf.actionName());
                    Object result = callable.call();
                    doRenderViewPhase(webRequest, controller, adf, result);
                    webRequest.completeAsync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        es.submit(r);
    }

    private boolean isCallableActionDefinition(ActionMethodDefinition adf) {
        return adf.getReturnType().equals(Callable.class);
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

}
