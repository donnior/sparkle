package me.donnior.sparkle.route;

import java.util.ArrayList;
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

    public RouteDefintion getRouteDefinition(String servletPath) {
        RouteDefintion rd = new RoutingBuilder();
        return rd;
    }
    
    public RoutingBuilder route(String url){
        // TODO deal the path variables in the url like '/user/:username/pictures'
        // TODO maps this url to the following router, 
//        logger.debug("registering new route {}", url);
        RoutingBuilder rb = new RoutingBuilder(url);
//        rb.setRoutePattern(url);
        this.routeBuilders.add(rb);
        return rb;
    }

    @Override
    public void install(RouteModule module) {
        logger.debug("install route module for {}", module.getClass() );
        module.config(this);
    }
    
    @Override
    public RoutingBuilder match(String cAndActionString) {
        for(RoutingBuilder rb : this.routeBuilders){
            if(rb.match(cAndActionString)){
                logger.debug("founded matched RoutingBuilder for {}", cAndActionString);
                Map<String, String> uriVariables = new AntPathMatcher().extractUriTemplateVariables(rb.getRoutePattern(), cAndActionString);
                logger.debug("extracted path variables {}", uriVariables);
                return rb;
            }
        }
        return null;
    }
    
}
