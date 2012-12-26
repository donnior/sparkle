package me.donnior.sparkle.route;

import java.util.List;

public interface Router {

    HttpScoppedRoutingBuilder route(String path);

    void install(RouteModule module);

    RoutingBuilder match(String cAndActionString);

    List<RoutingBuilder> getAllRouteBuilders();
    
}
