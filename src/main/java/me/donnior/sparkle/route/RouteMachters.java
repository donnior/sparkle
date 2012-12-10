package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

public class RouteMachters {

    public static RouteDefintion match(HttpServletRequest request) {
        //TODO match route defenition with request's servlet path, request headers, etc.
        RouteDefintion rd = new RoutingBuilder(){ //just demo
            @Override
            public String getControllerName() {
                return "user";
            }
            @Override
            public String getActionName() {
                return "index";
            }
        };
        return rd;
    }

}
