package me.donnior.sparkle.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.SparkleActionExecutor;
import me.donnior.sparkle.internal.ActionMethodDefinition;
import me.donnior.sparkle.internal.ActionMethodDefinitionFinder;
import me.donnior.sparkle.internal.ControllerScanner;
import me.donnior.sparkle.internal.ControllersHolder;
import me.donnior.sparkle.internal.RouteModuleScanner;
import me.donnior.sparkle.route.RouteMachter;
import me.donnior.sparkle.route.RouteModule;
import me.donnior.sparkle.route.RouterImpl;
import me.donnior.sparkle.route.RoutingBuilder;
import me.donnior.sparkle.view.JSPViewResolver;
import me.donnior.sparkle.view.ViewResolver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class SparkleDispatcherServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
    
    private ViewResolver viewResolver;
    private ControllersHolder controllersHolder;
    private RouterImpl router;

    private final static Logger logger = LoggerFactory.getLogger(SparkleDispatcherServlet.class);
    
    public SparkleDispatcherServlet() {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        
        //initialize Sparkle framework component
        //Scan controllers and stored their name and class
        this.viewResolver = new JSPViewResolver();
        this.controllersHolder = ControllersHolder.getInstance();
        this.router = RouterImpl.getInstance();
        scanControllers();
        installRouter();

        logger.info("sparkle framework started succeed within {} ms", stopwatch.elapsedMillis());
    }
    
    
    private void installRouter() {
        List<RouteModule> routeModules = new RouteModuleScanner().scanRouteModule();
        this.router.install(routeModules);
    }


    private void scanControllers() {
        // String controllerPackage = "me.donnior.sparkle.demo";
        String controllerPackage = "";
        this.controllersHolder.addControllers(new ControllerScanner().scanControllers(controllerPackage), true);
        
//        FMap<String, Class<?>> maps = new FHashMap<String, Class<?>>(this.controllersHolder.namedControllers());
//        maps.each(new MFunction<String, Class<?>>() {
//            public void apply(String key, Class<?> value) {
//                System.out.println(key +":" + value);
//            }
//        });
        
    }


    protected void doService(HttpServletRequest request, HttpServletResponse response, HTTPMethod method){
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        
//        System.out.println("context path : "+request.getContextPath());
//        System.out.println("servlet path : "+request.getServletPath());
//        System.out.println("request uri : "+request.getRequestURI());
//        System.out.println("path info: "+request.getPathInfo());
        RoutingBuilder rd = new RouteMachter(this.router).match(request);
        
        if(rd == null){
            response.setStatus(404);
            return;
        }
        String controllerName = rd.getControllerName();
        String actionName = rd.getActionName();
        Object controller = ControllerFactory.getController(controllerName);
        if(controller == null){
            System.out.println("can't find controller with name : " + controllerName);
            return;
        }
        
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(request);
            ((ApplicationController)controller).setResponse(response);
        }
        
        ActionMethodDefinition adf = new ActionMethodDefinitionFinder().find(controller.getClass(), actionName);
        
        Object result = new SparkleActionExecutor().invoke(adf, controller, request);
        
        
        //TODO set controller's instance varialbles which need to be used in view to the request.
        
        long actionTime = stopwatch.elapsedMillis();
        
        stopwatch.reset();
        stopwatch.start();
        if(result instanceof String){
            //TODO wrap the request and response to interface 'Context' for more view resolvers
            try {
                this.viewResolver.resovleView((String)result, request, response);

                long viewTime = stopwatch.stop().elapsedMillis();
                
                logger.info("completed request within {} ms (Action: {} ms | View: {} ms)" , 
                      new Object[]{viewTime + actionTime, actionTime, viewTime });
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
        }
        //invoke action on controller with proper argument, first should resovled argument
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       //if ... doService(req, resp, Method.DELETE); 
        doService(req, resp, HTTPMethod.GET);
    }
    
    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, HTTPMethod.HEAD);
    }
    
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, HTTPMethod.DELETE);
    }
    
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, HTTPMethod.OPTIONS);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, HTTPMethod.POST);
    }
    
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, HTTPMethod.PUT);
    }
    
    
    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, HTTPMethod.TRACE);
    }
    
    

}
