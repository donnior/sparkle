package me.donnior.sparkle.route;

import java.util.List;

public interface Router {

    HttpScoppedRoutingBuilder route(String path);

    void install(RouteModule module);

    RouteMatchRules match(String cAndActionString);

    List<RoutingBuilder> getAllRouteBuilders();
    
}
