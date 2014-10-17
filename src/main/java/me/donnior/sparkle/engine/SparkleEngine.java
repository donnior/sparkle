package me.donnior.sparkle.engine;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
import me.donnior.sparkle.core.resolver.ActionMethodDefinitionFinder;
import me.donnior.sparkle.core.resolver.ApplicationConfigScanner;
import me.donnior.sparkle.core.resolver.ControllerClassResolver;
import me.donnior.sparkle.core.resolver.ControllerScanner;
import me.donnior.sparkle.core.resolver.ControllersHolder;
import me.donnior.sparkle.core.resolver.RouteModuleScanner;
import me.donnior.sparkle.core.resolver.ViewRenderResolver;
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
        
        this.startup();
    }

    protected void startup() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        logger.info("Start initializing sparkle framework.");

        Application application = scanApplication();
        if(application != null){
            application.config(config);
        }else{
            logger.debug("not found any ApplicationConfig, will use the default configuration");
        }
        initEngineWithConfig(config);
        
        logger.info("sparkle framework started succeed within {} ms", stopwatch.elapsedMillis());
    }

 
    private void initEngineWithConfig(ConfigResult config) {
        
        //initialize Sparkle framework component

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
        List<ViewRender> viewRenders = this.envSpecific.getViewRendersManager().resovleRegisteredViewRenders(config.getCustomizedViewRenders());
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



    public void doService(final WebRequest webRequest, HTTPMethod method){
        WebResponse response = webRequest.getWebResponse();
        
        logger.info("processing request : {} {}", webRequest.getMethod(), webRequest.getPath());
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        InterceptorExecutionChain ic = new InterceptorExecutionChain(this.interceptors);
        boolean interceptorPassed = ic.doPreHandle(webRequest);
        if(!interceptorPassed){
            ic.doAfterHandle(webRequest);   //unnormal interceptor behaviour, ignore action exection and return after clean interceptors
            return;
        }
        
        RouteBuilder rd = this.routeBuilderResovler.match(webRequest);
        
        if(rd == null){
            logger.info("Counld not found matched route.\n");
            response.setStatus(HTTPStatusCode.NOT_FOUND);
            return;
        }
        
        Map<String, String> pathVariables = PathVariableDetector.extractPathVariables(rd, webRequest);
        webRequest.setAttribute(WebRequest.REQ_ATTR_FOR_PATH_VARIABLES, pathVariables);
        
        
        
        String controllerName = rd.getControllerName();
        
        Class<?> controllerClass = controllerClassResolver.getControllerClass(controllerName);

        final Object controller = this.controllerFactory.get(controllerName, controllerClass);
        
        if(controller == null){
            logger.error("can't find controller with name : " + controllerName);
            return;
        }
        
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(webRequest);
            ((ApplicationController)controller).setResponse(webRequest.getWebResponse());
        }
        
        String actionName = rd.getActionName();
        final ActionMethodDefinition adf = this.actionMethodResolver.find(controller.getClass(), actionName);
        
        boolean isAsyncAction = isAsyncActionDefinition(adf);
        if(isAsyncAction){
            //TODO start process action async, but should check the 
            //action result type is Callable, if not, wrap the action method in a Callable object
            boolean isCallableReturnType = false;
            Callable<Object> c = null;
            if(!isCallableReturnType){
                c = new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        return new ActionExecutor(envSpecific.getArgumentResolverManager()).invoke(adf, controller, webRequest);
                    }
                };
            } else {
                c = (Callable)new ActionExecutor(envSpecific.getArgumentResolverManager()).invoke(adf, controller, webRequest);
            }
            startAsyncProcess(c, webRequest);
            return;
        }
        //TODO add a exception hander here, process exception
        Object result = new ActionExecutor(envSpecific.getArgumentResolverManager()).invoke(adf, controller, webRequest);
        boolean isCallableResult = result instanceof Callable;
        if(isCallableResult){
            startAsyncProcess((Callable<Object>)result, webRequest);
            return;
        }
        
        long actionTime = stopwatch.elapsedMillis();
        stopwatch.reset();
        stopwatch.start();

        boolean isResponseProcessedMannually = isResponseProcessedManually(adf);
        if(!isResponseProcessedMannually){
            //TODO how to resolve view ? not just json or jsp, consider jsp, freemarker, vocility. Reference springmvc's viewResolver
            
            //get matched viewRender from all viewRenders, based on ActionMethodDefinition and result type
            // if found any matched viewRender, render view using it, pass 'controller instantance, result, request, response' as arguments
            // else use default viewRender(jsp view render to render result)
            ViewRender viewRender = this.viewRenderResolver.resolveViewRender(adf, result);
            if(viewRender != null){
                try {
//                    Map<String, Object> valueToExpose = null;
//                    if(viewRender.needPrepareValue()){
//                        valueToExpose = getValueMapFromContrller(controller);
//                    }
                    viewRender.renderView(result, controller, webRequest);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            logger.debug("Http servlet response has been procceed mannually, ignore view rendering.");
        }
        
      //TODO maybe should put this in a try-catch-finally clause? We should cleanup interceptors even action execution throws exception
        if(interceptorPassed){
            ic.doAfterHandle(webRequest);   
        }
        
        long viewTime = stopwatch.stop().elapsedMillis();
        logger.info("completed request within {} ms (Action: {} ms | View: {} ms)\n", 
                new Object[]{viewTime + actionTime, actionTime, viewTime });
        
        //TODO set controller's instance varialbles which need to be used in view to the request.
    }
    
    /**
     * if one action method has a HttpServletResponse argument, then suppose user want process response manually,
     * will ignore the view rendering phase.
     * @param adf
     * @return
     */
    private boolean isResponseProcessedManually(ActionMethodDefinition adf) {
        return this.envSpecific.getLifeCycleManager().isResponseProcessedManually(adf);
    }

    private ExecutorService es = Executors.newFixedThreadPool(100);
    
    void startAsyncProcess(Callable<Object> callable, WebRequest webRequest){
        //TODO process async callable
        /*
        final AsyncContext ac = webRequest.getServletRequest().startAsync();
        es.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                try {
                    ac.getResponse().getWriter().write("hello");
                    ac.complete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
        */
    }
    
    private boolean isAsyncActionDefinition(ActionMethodDefinition adf) {
        return adf.hasAnnotation(Async.class);
    }

    
    
    private void installRouter() {
        String routePackage = "";
        List<RouteModule> routeModules = new RouteModuleScanner().scanRouteModule(routePackage);
        this.router.install(routeModules);
    }

    private Application scanApplication() {
        Class<?> clz = new ApplicationConfigScanner().scan("");
        return clz != null ? (Application) ReflectionUtil.initialize(clz) : null;
    }

}
