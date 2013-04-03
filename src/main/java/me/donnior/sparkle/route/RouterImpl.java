package me.donnior.sparkle.route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouterImpl implements Router, RouteBuilderHolder, RouteModuleInstallable {

    private static RouterImpl instance = new RouterImpl();
    
    private List<RoutingBuilder> routeBuilders = new ArrayList<RoutingBuilder>();
    
    private final static Logger logger = LoggerFactory.getLogger(RouterImpl.class);
    
    public static RouterImpl getInstance() {
        return instance;
    }
    
    public List<RoutingBuilder> getRegisteredRouteBuilders() {
        return Collections.unmodifiableList(this.routeBuilders);
    }
    
    public RoutingBuilder match(String url){
        RoutingBuilder rb = new RoutingBuilder(url);
        this.routeBuilders.add(rb);
        return rb;
    }
    
    @Override
    public void install(RouteModule module) {
        logger.debug("install route module for {}", module.getClass() );
        module.config(this);
    }
    
    @Override
    public void install(Collection<RouteModule> modules) {
        for(RouteModule module : modules){
            this.install(module);
        }
    }
    
    public void clear(){
        this.routeBuilders.clear();
    }
    
}
