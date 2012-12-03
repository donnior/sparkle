package pomtask.rest.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.route.RouteDefintion;
import me.donnior.sparkle.route.Router;

public class SparkleDispatcherServlet extends HttpServlet {
    
    
    enum Method  {GET, PUT, POST, DELETE, OPTIONS, HEAD, TRACE};
    
    public SparkleDispatcherServlet() {
        //initialize Sparkle framework component
        //Scan controllers and stored their name and class
    }
    
    
    protected void doService(HttpServletRequest request, HttpServletResponse response, Method method){
        RouteDefintion rd = Router.getInstance().getRouteDefinition(request.getServletPath());
        String controllerName = rd.getControllerName();
        String actionName = rd.getActionName();
        Object controller = ControllerFactory.getController(controllerName);
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(request);
            ((ApplicationController)controller).setResponse(response);
        }
        //invoke action on controller with proper argument, first should resovled argument
    }
    
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
       //if ... doService(req, resp, Method.DELETE); 
        doService(req, resp, Method.GET);
    }
    
    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, Method.HEAD);
    }
    
    
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, Method.DELETE);
    }
    
    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, Method.OPTIONS);
    }
    
    
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, Method.POST);
    }
    
    
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, Method.PUT);
    }
    
    
    @Override
    protected void doTrace(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doService(req, resp, Method.TRACE);
    }
    
    

}
