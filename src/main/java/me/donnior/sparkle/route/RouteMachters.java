package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

public class RouteMachters {

    public static RouteDefintion match(HttpServletRequest request) {
        //TODO match route defenition with request's servlet path, request headers, etc.
        
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String cAndActionString = pathInfo;
        
        if(pathInfo == null){
            System.out.println("wild servlet mapping like / or *.do");
            cAndActionString = request.getServletPath();
        } else {
            System.out.println("normal mapping like /cms/*");
//            cAndActionString = request.getServletPath();
        }
        
        System.out.println("c&a string : " + cAndActionString);
        
        //get route definitioin from router cache and got the controller and action name
        
        final String controllerName = request.getRequestURI();
        RouteDefintion rd = new RoutingBuilder(){ //just demo
            @Override
            public String getControllerName() {
                return controllerName.substring(1);
            }
            @Override
            public String getActionName() {
                return "index";
            }
        };
        return rd;
    }

}
