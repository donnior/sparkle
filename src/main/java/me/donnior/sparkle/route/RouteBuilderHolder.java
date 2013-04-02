package me.donnior.sparkle.route;

import java.util.List;

public interface RouteBuilderHolder {

    List<RoutingBuilder> getRegisteredRouteBuilders();
    
}
