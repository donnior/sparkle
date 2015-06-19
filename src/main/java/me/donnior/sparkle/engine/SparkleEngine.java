package me.donnior.sparkle.engine;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

import me.donnior.fava.FArrayList;
import me.donnior.fava.FList;
import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.WebResponse;
import me.donnior.sparkle.annotation.Async;
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

public class SparkleEngine {

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
            logger.debug("not found any ApplicationConfig, will use the default configuration");
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
        Map<String, Class<?>> scanedControllers = new ControllerScanner().scanControllers(this.config.getBasePackage());
        this.controllerClassResolver.registeControllers(scanedControllers, true);
    }

    private boolean executePreInterceptor(InterceptorExecutionChain interceptorExecutionChain,  WebRequest webRequest){
        boolean interceptorPassed = interceptorExecutionChain.doPreHandle(webRequest);
        return interceptorPassed;
    }

    private void executeAfterInterceptor(InterceptorExecutionChain interceptorExecutionChain,  WebRequest webRequest){
        interceptorExecutionChain.doAfterHandle(webRequest);
    }

    private Object getControlerInstanceForRoute(RouteBuilder rd){
        String controllerName = rd.getControllerName();
        final Class<?> controllerClass = this.controllerClassResolver.getControllerClass(controllerName);
        final Object controller  = this.controllerFactory.get(controllerName, controllerClass);
        if(controller == null){
            logger.error("Can't get controller instance with name : {} and class : {}", controllerName, controllerClass);
            throw new RuntimeException("Can't get controller instance with name : " + controllerName + " and class : "+ controllerClass);
        }
        return controller;
    }

    public void doService(final WebRequest webRequest, HTTPMethod method){
        logger.info("Processing request : {} {}", webRequest.getMethod(), webRequest.getPath());
        Stopwatch stopwatch = Stopwatch.createStarted();

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
        WebResponse response = webRequest.getWebResponse();
        if(rd == null){
            logger.info("Could not find route for request : '{}' \n", webRequest);
            response.setStatus(HTTPStatusCode.NOT_FOUND);
            return;
        }

        //create controller object
        final Object controller = getControlerInstanceForRoute(rd);
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(webRequest);
            ((ApplicationController)controller).setResponse(webRequest.getWebResponse());
        }

        //extract path variables
        setPathVariablesToRequestAttribute(webRequest, rd);

        final ActionMethodDefinition adf = this.actionMethodResolver.find(controller.getClass(), rd.getActionName());

        if(isAsyncActionDefinition(adf)){
            logger.info("Action is annotated with @Async, start processing as async request");
            processAsyncRequest(webRequest, controller, this.argumentResolverManager, adf);
            return;
        }

        //TODO add a exception hander here, process exception
        Object result = new ActionExecutor(this.argumentResolverManager).invoke(adf, controller, webRequest);

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
            logger.debug("Http servlet response has been procceed mannually, ignore view rendering.");
        }
        
        //TODO maybe should put this in a try-catch-finally clause? We should cleanup interceptors even action execution throws exception
        if(interceptorPassed){
            ic.doAfterHandle(webRequest);   
        }
        
        long viewTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        logger.info("Completed request within {} ms (Action: {} ms | View: {} ms)\n", new Object[]{viewTime + actionTime, actionTime, viewTime });
    }

    private void setPathVariablesToRequestAttribute(WebRequest webRequest, RouteBuilder rd) {
        Map<String, String> pathVariables = PathVariableDetector.extractPathVariables(rd, webRequest);
        webRequest.setAttribute(WebRequest.REQ_ATTR_FOR_PATH_VARIABLES, pathVariables);
    }

    private void doRenderViewPhase(WebRequest webRequest, Object controller, ActionMethodDefinition adf, Object result) {

        //TODO how to resolve view ? not just json or jsp, consider jsp, freemarker, vocility.
        //Reference springmvc's viewResolver
        logger.debug("Render view for {}#{} with result type {}", controller.getClass().getSimpleName(), adf.actionName(), result.getClass().getSimpleName());
        ViewRender viewRender = this.viewRenderResolver.resolveViewRender(adf, result);
        if(viewRender != null){
            try {
                viewRender.renderView(result, controller, webRequest);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processAsyncRequest(final WebRequest webRequest, final Object controller, final ArgumentResolverManager arm, final ActionMethodDefinition adf) {
        //TODO start process action async, but should check action result type is Callable, if not, wrap the action method in a Callable object
        boolean isCallableReturnType = adf.getReturnType().getClass().equals(Callable.class);
        Callable<Object> c = null;
        if(!isCallableReturnType){
            c = new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return new ActionExecutor(arm).invoke(adf, controller, webRequest);
                }
            };
        } else {
            c = (Callable)new ActionExecutor(arm).invoke(adf, controller, webRequest);
        }
        startAsyncProcess(c, webRequest, controller, adf);
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
    
    private void startAsyncProcess(Callable<Object> callable, WebRequest webRequest, Object controller, ActionMethodDefinition adf){
        webRequest.startAsync();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    logger.debug("Execute action asynchronously for {}#{}", controller.getClass().getSimpleName(), adf.actionName());
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
    
    private boolean isAsyncActionDefinition(ActionMethodDefinition adf) {
        return adf.hasAnnotation(Async.class);
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
