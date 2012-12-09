package me.donnior.sparkle.route;

import me.donnior.sparkle.HTTPMethod;

public class HTTPSensoredRouter {
    
    private HTTPMethod httpMethod;
    private String actionName;
    private String controllerName;

    public HTTPSensoredRouter withGet(){
        this.httpMethod = HTTPMethod.GET ;
        return this;
    }
    
    public HTTPSensoredRouter withPost(){
        this.httpMethod = HTTPMethod.POST ;
        return this;
    }
    
    public HTTPSensoredRouter to(String route){
        this.controllerName = extractController(route);
        this.actionName = extractAction(route);
        return this;
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
