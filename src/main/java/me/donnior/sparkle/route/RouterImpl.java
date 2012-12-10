package me.donnior.sparkle.route;

public class RouterImpl implements Router {

    private static RouterImpl instance = new RouterImpl();
    
    public static RouterImpl getInstance() {
        return instance;
    }

    public RouteDefintion getRouteDefinition(String servletPath) {
        RouteDefintion rd = new RoutingBuilder();
        return rd;
    }
    
    public RoutingBuilder route(String url){
        // TODO deal the path variables in the url like '/user/:username/pictures'
        // TODO maps this url to the following router, 
        return new RoutingBuilder();
    }

}
