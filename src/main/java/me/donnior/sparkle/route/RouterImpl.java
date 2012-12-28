package me.donnior.sparkle.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import me.donnior.sparkle.util.AntPathMatcher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouterImpl implements Router {

    private static RouterImpl instance = new RouterImpl();
    
    private List<RoutingBuilder> routeBuilders = new ArrayList<RoutingBuilder>();
    
    private final static Logger logger = LoggerFactory.getLogger(RouterImpl.class);
    
    public static RouterImpl getInstance() {
        return instance;
    }
    
    @Override
    public List<RoutingBuilder> getAllRouteBuilders() {
        return Collections.unmodifiableList(this.routeBuilders);
    }
    
    public RoutingBuilder route(String url){
        RoutingBuilder rb = new RoutingBuilder(url);
        this.routeBuilders.add(rb);
        return rb;
    }
    
    @Override
    public void install(RouteModule module) {
        logger.debug("install route module for {}", module.getClass() );
        module.config(this);
    }
    
}
