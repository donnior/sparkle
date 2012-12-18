package me.donnior.sparkel.servlet;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.SparkleActionExecutor;
import me.donnior.sparkle.annotation.Controller;
import me.donnior.sparkle.internal.ControllerScanner;
import me.donnior.sparkle.internal.ControllersHolder;
import me.donnior.sparkle.route.RouteDefintion;
import me.donnior.sparkle.route.RouteMachters;
import me.donnior.sparkle.view.JSPViewResolver;
import me.donnior.sparkle.view.ViewResolver;

import org.reflections.Reflections;

public class SparkleDispatcherServlet extends HttpServlet {
    
    public static final String   INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
    
    private ViewResolver viewResolver;
    private ControllersHolder controllersHolder;
    
    public SparkleDispatcherServlet() {
        //initialize Sparkle framework component
        //Scan controllers and stored their name and class
        this.viewResolver = new JSPViewResolver();
        this.controllersHolder = ControllersHolder.getInstance();
        scanControllers();
    }
    
    
    private void scanControllers() {
        this.controllersHolder.addControllers(new ControllerScanner().scanControllers("me.donnior.sparkle.demo"), true);
        for(Map.Entry<String, Class<?>> entry : this.controllersHolder.namedControllers().entrySet()){
            System.out.println(entry.getKey() +":" + entry.getValue());
        }
    }


    protected void doService(HttpServletRequest request, HttpServletResponse response, HTTPMethod method){

        //RouteDefintion rd = Router.getInstance().getRouteDefinition(request.getServletPath());
        RouteDefintion rd = RouteMachters.match(request);
        
        
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
        if(result instanceof String){
            //TODO wrap the request and response to interface 'Context' for more view resolvers
            try {
                this.viewResolver.resovleView((String)result, request, response);
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
