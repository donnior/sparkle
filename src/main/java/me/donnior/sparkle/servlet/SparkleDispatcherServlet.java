package me.donnior.sparkle.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.fava.FArrayList;
import me.donnior.fava.FList;
import me.donnior.fava.Predict;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.SparkleActionExecutor;
import me.donnior.sparkle.controller.ApplicationController;
import me.donnior.sparkle.http.HTTPStatusCode;
import me.donnior.sparkle.internal.ActionMethodDefinition;
import me.donnior.sparkle.internal.ActionMethodDefinitionFinder;
import me.donnior.sparkle.internal.ControllerScanner;
import me.donnior.sparkle.internal.ControllersHolder;
import me.donnior.sparkle.internal.RouteModuleScanner;
import me.donnior.sparkle.route.RouteBuilder;
import me.donnior.sparkle.route.RouteMachter;
import me.donnior.sparkle.route.RouteModule;
import me.donnior.sparkle.route.RouterImpl;
import me.donnior.sparkle.view.JSONViewRender;
import me.donnior.sparkle.view.JSPViewRender;
import me.donnior.sparkle.view.ViewRender;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;

public class SparkleDispatcherServlet extends HttpServlet {
    
    private static final long serialVersionUID = 1L;

    public static final String INCLUDE_REQUEST_URI_ATTRIBUTE = "javax.servlet.include.request_uri";
    
    private ViewRender viewResolver;
    private FList<ViewRender> viewRenders = new FArrayList<ViewRender>();
    private ControllersHolder controllersHolder;
    private RouterImpl router;

    private final static Logger logger = LoggerFactory.getLogger(SparkleDispatcherServlet.class);
    
    public SparkleDispatcherServlet(SparkleConfiguration config) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();
        
        //initialize Sparkle framework component
        //Scan controllers and stored their name and class
        this.viewResolver = new JSPViewRender();
        this.viewRenders.add(new JSONViewRender());
        this.viewRenders.add(this.viewResolver);
        
        this.controllersHolder = ControllersHolder.getInstance();
        this.router = RouterImpl.getInstance();
        scanControllers();
        installRouter();

        logger.info("sparkle framework started succeed within {} ms", stopwatch.elapsedMillis());
    }
    
    
    private void installRouter() {
        String routePackage = "";
        List<RouteModule> routeModules = new RouteModuleScanner().scanRouteModule(routePackage);
        this.router.install(routeModules);
    }


    private void scanControllers() {
        String controllerPackage = "";
        this.controllersHolder.addControllers(new ControllerScanner().scanControllers(controllerPackage), true);

    }


    protected void doService(HttpServletRequest request, HttpServletResponse response, HTTPMethod method){
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.start();

        RouteBuilder rd = new RouteMachter(this.router).match(request);
        
        if(rd == null){
            response.setStatus(HTTPStatusCode.NOT_FOUND);
            return;
        }
        String controllerName = rd.getControllerName();
        String actionName = rd.getActionName();
        Object controller = ControllerFactory.getController(controllerName);
        if(controller == null){
            logger.error("can't find controller with name : " + controllerName);
            return;
        }
        
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(request);
            ((ApplicationController)controller).setResponse(response);
        }
        
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

              logger.info("completed request within {} ms (Action: {} ms | View: {} ms)" , 
                    new Object[]{viewTime + actionTime, actionTime, viewTime });
            } catch (ServletException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        
        //TODO set controller's instance varialbles which need to be used in view to the request.
        
        
//        if(result instanceof String){
//            //TODO wrap the request and response to interface 'Context' for more view resolvers
//            try {
//                this.viewResolver.renderView((String)result, request, response);
//
//                long viewTime = stopwatch.stop().elapsedMillis();
//                
//                logger.info("completed request within {} ms (Action: {} ms | View: {} ms)" , 
//                      new Object[]{viewTime + actionTime, actionTime, viewTime });
//            } catch (ServletException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            
//        }
    }
    
    private ViewRender findMatchedViewRender(final ActionMethodDefinition adf, final Object result) {
        return this.viewRenders.find(new Predict<ViewRender>() {
            @Override
            public boolean apply(ViewRender viewRender) {
                return viewRender.supportActionMethod(adf, result);
            }
        });
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
