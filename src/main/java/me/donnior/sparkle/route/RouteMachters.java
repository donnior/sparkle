package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

public class RouteMachters {

    public static RouteDefintion match(HttpServletRequest request) {
        //TODO match route defenition with request's servlet path, request headers, etc.
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
