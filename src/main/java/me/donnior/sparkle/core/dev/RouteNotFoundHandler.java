package me.donnior.sparkle.core.dev;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.route.RouteBuilder;
import me.donnior.sparkle.core.route.RouteBuilderHolder;

import java.util.List;

public class RouteNotFoundHandler extends DevelopmentErrorHandler{


    private final RouteBuilderHolder routes;

    public RouteNotFoundHandler(RouteBuilderHolder routes){
        this.routes = routes;
    }
    public String doHandle(WebRequest webRequest) {
        StringBuilder sb = new StringBuilder("<div><h2>All Available  Routes</h2><ul>");
        for (RouteBuilder rb : this.routes.getRegisteredRouteBuilders()){
            sb.append(liForRouteBuilder(rb));
        }
        sb.append("</ul></div>");
        return sb.toString();
    }

    private String liForRouteBuilder(RouteBuilder rb) {
        return "<li>" + rb.getHttpMethod() + " " + rb.getPathPattern() + "</li>";
    }
}
