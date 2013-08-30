package me.donnior.sparkle.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.fava.Consumer;
import me.donnior.fava.FArrayList;
import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.SparkleActionExecutor;
import me.donnior.sparkle.config.Application;
import me.donnior.sparkle.config.Config;
import me.donnior.sparkle.config.ConfigAware;
import me.donnior.sparkle.config.ConfigImpl;
import me.donnior.sparkle.controller.ApplicationController;
import me.donnior.sparkle.http.HTTPStatusCode;
import me.donnior.sparkle.internal.ActionMethodDefinition;
import me.donnior.sparkle.internal.ActionMethodDefinitionFinder;
import me.donnior.sparkle.internal.ApplicationConfigScanner;
import me.donnior.sparkle.internal.ControllerScanner;
import me.donnior.sparkle.internal.ControllersHolder;
import me.donnior.sparkle.internal.RouteModuleScanner;
import me.donnior.sparkle.route.RouteBuilder;
import me.donnior.sparkle.route.RouteMachter;
import me.donnior.sparkle.route.RouteModule;
import me.donnior.sparkle.route.RouterImpl;
import me.donnior.sparkle.view.JSONViewRender;
import me.donnior.sparkle.view.JSPViewRender;
import me.donnior.sparkle.view.RedirectViewRender;
import me.donnior.sparkle.view.ViewRender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class SparkleEngine {

    private FList<ViewRender> viewRenders = new FArrayList<ViewRender>();
    private RouterImpl router;
    private ConfigImpl config = new ConfigImpl();
    private ControllerFactory controllerFactory = new GuiceControllerFactory();
    
    private final static Logger logger = LoggerFactory.getLogger(SparkleEngine.class);
    
    public SparkleEngine(Config config){
        this();
    }

    public SparkleEngine(){
        this.startup();
    }

    protected void startup() {
        Application application = scanApplication();
        if(application != null){
            application.config(config);
        }else{
            logger.debug("not found any ApplicationConfig, will use the default configuration");
        }
        initEngineWithConfig(config);
    }

 
    private void initEngineWithConfig(ConfigAware config) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        
        //initialize Sparkle framework component
        
        initViewRenders(config.getViewRenders());
        
        initViewControllers(config.getControllerPackages());
        
        initControllerFactory(config);
        
        this.router = RouterImpl.getInstance();
        
        installRouter();

        logger.info("sparkle framework started succeed within {} ms", stopwatch.elapsedMillis());
        
    }

    //TODO how to make the controller factory can be customized, for example let user use an
    //spring container as this factory? Maybe introduce a ControllerFactoryResolver based on ConfigAware is better?
    private void initControllerFactory(ConfigAware config) {
        if(config.getControllerFactory() != null){
            this.controllerFactory = config.getControllerFactory();
            return;
        }
        if(config.getControllerFactoryClass() != null){
            this.controllerFactory = (ControllerFactory)ReflectionUtil.initialize(config.getControllerFactoryClass());
            return;
        }
    }

    private void initViewControllers(String[] controllerPackages) {
        //TODO how to deal with multi controller packages
        scanControllers(this.config.getBasePackage());
    }

    private void initViewRenders(FList<Class<? extends ViewRender>> renders) {
        renders.each(new Consumer<Class<? extends ViewRender>>() {
            @Override
            public void apply(Class<? extends ViewRender> viewRenderClass) {
                viewRenders.add((ViewRender)ReflectionUtil.initialize(viewRenderClass));
            }
        });
        ensuerDefaultViewRenders();
    }

    private void ensuerDefaultViewRenders() {
        this.viewRenders.add(new JSONViewRender());
        this.viewRenders.add(new RedirectViewRender());
        this.viewRenders.add(new JSPViewRender());
    }

    protected void doService(HttpServletRequest request, HttpServletResponse response, HTTPMethod method){
        logger.info("processing request : {}", request);
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        RouteBuilder rd = new RouteMachter(this.router).match(request);
        
        if(rd == null){
            response.setStatus(HTTPStatusCode.NOT_FOUND);
            return;
        }
        String controllerName = rd.getControllerName();
        Class<?> controllerClass = ControllersHolder.getInstance().getControllerClass(controllerName);

        Object controller = this.controllerFactory.get(controllerName, controllerClass);
//        Object controller = SimpleControllerFactory.getController(controllerName);
        
        if(controller == null){
            logger.error("can't find controller with name : " + controllerName);
            return;
        }
        
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(request);
            ((ApplicationController)controller).setResponse(response);
        }
        
        String actionName = rd.getActionName();
        ActionMethodDefinition adf = new ActionMethodDefinitionFinder().find(controller.getClass(), actionName);
        
        Object result = new SparkleActionExecutor().invoke(adf, controller, request);
        
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


    private void scanControllers(String pkg) {
        ControllersHolder.getInstance().addControllers(new ControllerScanner().scanControllers(pkg), true);
    }

    
    private Application scanApplication() {
        Class<?> clz = new ApplicationConfigScanner().scan("");
        return clz != null ? (Application) ReflectionUtil.initialize(clz) : null;
    }

    
}
