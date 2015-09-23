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
        StringBuilder sb = new StringBuilder("<div><h2>All Available  Routes</h2>");
        sb.append("<table class=\"debug-table\"><thead><tr><th>HTTP Verb</th><th>Path</th><th>Controller#Action</th></tr></thead></tbody>");
        for (RouteBuilder rb : this.routes.getRegisteredRouteBuilders()){
            sb.append(liForRouteBuilder(rb));
        }
        sb.append("</table></div>");
        return sb.toString();
    }

    private String liForRouteBuilder(RouteBuilder rb) {
        String result = "<tr><td>" + rb.getHttpMethod() + "</td><td>" + rb.getPathPattern() + "</td><td>" ;
        String to     = rb.isFunctionRoute() ? "(req, res) -> {.....}" : rb.getControllerName() + "#" + rb.getActionName();
        return result + to + "</td></tr>";
    }
}
