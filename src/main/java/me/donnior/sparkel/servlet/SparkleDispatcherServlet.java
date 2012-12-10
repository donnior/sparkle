package me.donnior.sparkel.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.SparkleActionExecutor;
import me.donnior.sparkle.route.RouteDefintion;
import me.donnior.sparkle.route.RouteMachters;
import me.donnior.sparkle.route.RouterImpl;
import me.donnior.sparkle.view.JSPViewResolver;
import me.donnior.sparkle.view.ViewResolver;

public class SparkleDispatcherServlet extends HttpServlet {
    
    
    private ViewResolver viewResolver;
    
    public SparkleDispatcherServlet() {
        //initialize Sparkle framework component
        //Scan controllers and stored their name and class
        this.viewResolver = new JSPViewResolver();
    }
    
    
    protected void doService(HttpServletRequest request, HttpServletResponse response, HTTPMethod method){
        //RouteDefintion rd = Router.getInstance().getRouteDefinition(request.getServletPath());
        RouteDefintion rd = RouteMachters.match(request);
        if(rd == null){
            //TODO dealing with miss match
            return; 
        }
        String controllerName = rd.getControllerName();
        String actionName = rd.getActionName();
        Object controller = ControllerFactory.getController(controllerName);
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(request);
            ((ApplicationController)controller).setResponse(response);
        }
        Object result = new SparkleActionExecutor(request, response).invokeAction(controller, actionName);
        if(result instanceof String){
            //TODO wrap the request and response to interface 'Context' for more view resolvers
            this.viewResolver.resovleView((String)result, request, response);
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
