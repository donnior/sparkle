package me.donnior.sparkle.route;

public class Router {

    private static Router instance = new Router();
    
    public static Router getInstance() {
        return instance;
    }

    public RouteDefintion getRouteDefinition(String servletPath) {
        RouteDefintion rd = new RouteDefintion();
        return rd;
    }
    
    public HTTPSensoredRouter route(String url){
        // TODO deal the path variables in the url like '/user/:username/pictures'
        // TODO maps this url to the following router, 
        return new HTTPSensoredRouter();
    }

}
