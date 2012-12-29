package me.donnior.sparkle.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.SparkleActionExecutor;
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

public class SparkleDispatcherServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
    
    private ViewResolver viewResolver;
    private ControllersHolder controllersHolder;
    private RouterImpl router;

    private final static Logger logger = LoggerFactory.getLogger(SparkleDispatcherServlet.class);
    
    public SparkleDispatcherServlet() {
        //initialize Sparkle framework component
        //Scan controllers and stored their name and class
        this.viewResolver = new JSPViewResolver();
        this.controllersHolder = ControllersHolder.getInstance();
        this.router = RouterImpl.getInstance();
        scanControllers();
        installRouter();
    }
    
    
    private void installRouter() {
        List<? extends RouteModule> routeModules = new RouteModuleScanner().scanRouteModule();
        for(RouteModule module : routeModules){
            this.router.install(module);
        }
        
    }


    private void scanControllers() {
        // String controllerPackage = "me.donnior.sparkle.demo";
        String controllerPackage = "";
        this.controllersHolder.addControllers(new ControllerScanner().scanControllers(controllerPackage), true);
        for(Map.Entry<String, Class<?>> entry : this.controllersHolder.namedControllers().entrySet()){
            System.out.println(entry.getKey() +":" + entry.getValue());
        }
    }


    protected void doService(HttpServletRequest request, HttpServletResponse response, HTTPMethod method){
        
//        System.out.println("context path : "+request.getContextPath());
//        System.out.println("servlet path : "+request.getServletPath());
//        System.out.println("request uri : "+request.getRequestURI());
//        System.out.println("path info: "+request.getPathInfo());
        long start = System.currentTimeMillis();
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
        Object result = new SparkleActionExecutor(request, response).invokeAction(controller, actionName);

        long actionEnd = System.currentTimeMillis();

        if(result instanceof String){
            //TODO wrap the request and response to interface 'Context' for more view resolvers
            try {
                this.viewResolver.resovleView((String)result, request, response);

                long viewEnd = System.currentTimeMillis();
                logger.info("completed request within {} ms (Action: {} ms | View: {} ms)" , 
                    new Object[]{viewEnd - start, actionEnd - start, viewEnd - actionEnd });
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
