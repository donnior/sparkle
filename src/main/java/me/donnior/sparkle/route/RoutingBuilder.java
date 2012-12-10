package me.donnior.sparkle.route;

import java.util.List;

import me.donnior.sparkle.HTTPMethod;

public class RoutingBuilder implements HttpScoppedRoutingBuilder, RouteDefintion{
    
    private HTTPMethod httpMethod;
    private String actionName;
    private String controllerName;

    public RoutingBuilder() {
        
    }
    
    public RoutingBuilder(Router router, List<Object> elements, Object source, String path) {
        this.httpMethod = HTTPMethod.GET;
    }
    
    @Override
    public RoutingBuilder withGet(){
        this.httpMethod = HTTPMethod.GET ;
        return this;
    }
    
//    @Override
    public RoutingBuilder withPost(){
        this.httpMethod = HTTPMethod.POST ;
        return this;
    }
    
    @Override
    public void to(String route){
        this.controllerName = extractController(route);
        this.actionName = extractAction(route);
//        return this;
    }
    
    @Override
    public String getActionName() {
        return this.actionName;
    }
    
    @Override
    public String getControllerName() {
        return this.controllerName;
    }

    private String extractController(String route) {
        if(route == null || route.trim().equals("")){
            throw new RuntimeException("The controller name is empty, you can't bind route to it");
        }
        return route.split("#")[0];
    }

    private String extractAction(String route) {
        // TODO deal route without action, means to default action 'index'
        return route.split("#")[1];
    }

}
