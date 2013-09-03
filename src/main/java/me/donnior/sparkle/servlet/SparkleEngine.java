package me.donnior.sparkle.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.fava.FArrayList;
import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.annotation.Async;
import me.donnior.sparkle.config.Application;
import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.resolver.ActionMethodDefinitionFinder;
import me.donnior.sparkle.core.resolver.ApplicationConfigScanner;
import me.donnior.sparkle.core.resolver.ControllerClassResolver;
import me.donnior.sparkle.core.resolver.ControllerScanner;
import me.donnior.sparkle.core.resolver.ControllersHolder;
import me.donnior.sparkle.core.resolver.RouteModuleScanner;
import me.donnior.sparkle.core.route.RouteBuilder;
import me.donnior.sparkle.core.route.RouteBuilderResolver;
import me.donnior.sparkle.core.route.RouterImpl;
import me.donnior.sparkle.core.route.SimpleRouteBuilderResolver;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.core.view.ViewRendersResovler;
import me.donnior.sparkle.http.HTTPStatusCode;
import me.donnior.sparkle.route.RouteModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class SparkleEngine {

    private FList<ViewRender> viewRenders;
    private RouterImpl router;
    private ConfigImpl config;
    private ControllerFactory controllerFactory;
    private RouteBuilderResolver routeBuilderResovler;
    private ControllerClassResolver controllerClassResolver;
    
    private final static Logger logger = LoggerFactory.getLogger(SparkleEngine.class);
    
    public SparkleEngine(){
        this.config                  = new ConfigImpl();
        this.viewRenders             = new FArrayList<ViewRender>();
        this.router                  = RouterImpl.getInstance();
        this.controllerFactory       = new GuiceControllerFactory();
        this.routeBuilderResovler    = new SimpleRouteBuilderResolver(this.router);
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
        
    }

    private void initViewRenders(ConfigResult config) {
        this.viewRenders.addAll(new ViewRendersResovler().resovleRegisteredViewRenders(config.getCustomizedViewRenders()));
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



    protected void doService(final HttpServletRequest request, final HttpServletResponse response, HTTPMethod method){
        logger.info("processing request : {}", request.getRequestURI());
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        RouteBuilder rd = this.routeBuilderResovler.match(request);
        
        if(rd == null){
            response.setStatus(HTTPStatusCode.NOT_FOUND);
            return;
        }
        String controllerName = rd.getControllerName();
        
        Class<?> controllerClass = controllerClassResolver.getControllerClass(controllerName);

        final Object controller = this.controllerFactory.get(controllerName, controllerClass);
        
        if(controller == null){
            logger.error("can't find controller with name : " + controllerName);
            return;
        }
        
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(request);
            ((ApplicationController)controller).setResponse(response);
        }
        
        String actionName = rd.getActionName();
        final ActionMethodDefinition adf = new ActionMethodDefinitionFinder().find(controller.getClass(), actionName);
        
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
                        return new SparkleActionExecutor().invoke(adf, controller, request, response);
                    }
                };
            } else {
                c = (Callable)new SparkleActionExecutor().invoke(adf, controller, request, response);
            }
            startAsyncProcess(c, request, response);
            return;
        }
        
        Object result = new SparkleActionExecutor().invoke(adf, controller, request, response);
        boolean isCallableResult = result instanceof Callable;
        if(isCallableResult){
            startAsyncProcess((Callable<Object>)result, request, response);
            return;
        }
        
        long actionTime = stopwatch.elapsedMillis();
        stopwatch.reset();
        stopwatch.start();

        //TODO how to resolve view ? not just json or jsp, consider jsp, freemarker, vocility. Reference springmvc's viewResolver
        
        
        //get matched viewRender from all viewRenders, based on ActionMethodDefinition and result type
        // if found any matched viewRender, render view using it, pass 'controller instantance, result, request, response' as arguments
        // else use default viewRender(jsp view render to render result)
        ViewRender viewRender = findMatchedViewRender(adf, result);
        if(viewRender != null){
            try {
                viewRender.renderView(result, request, response);
                long viewTime = stopwatch.stop().elapsedMillis();

              logger.info("completed request within {} ms (Action: {} ms | View: {} ms)", 
                    new Object[]{viewTime + actionTime, actionTime, viewTime });
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        //TODO set controller's instance varialbles which need to be used in view to the request.
  
    }
    
    private ExecutorService es = Executors.newFixedThreadPool(100);
    
    void startAsyncProcess(Callable<Object> callable, HttpServletRequest request, final HttpServletResponse response){
        //TODO process callable
//        System.out.println("async process");
        final AsyncContext ac = request.startAsync();
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
    }
    
    private boolean isAsyncActionDefinition(ActionMethodDefinition adf) {
        return adf.hasAnnotation(Async.class);
    }

    private ViewRender findMatchedViewRender(final ActionMethodDefinition adf, final Object result) {
        return this.viewRenders.find(new Predicate<ViewRender>() {
            @Override
            public boolean apply(ViewRender viewRender) {
                return viewRender.supportActionMethod(adf, result);
            }
        });
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
