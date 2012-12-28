package me.donnior.sparkle.route;

import java.util.List;

public interface Router {

    HttpScoppedRoutingBuilder route(String path);

    void install(RouteModule module);


    List<RoutingBuilder> getAllRouteBuilders();
    
}
