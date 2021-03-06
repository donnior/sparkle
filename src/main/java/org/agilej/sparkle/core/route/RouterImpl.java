package org.agilej.sparkle.core.route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.agilej.sparkle.route.RouteModule;
import org.agilej.sparkle.route.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouterImpl implements Router, RouteBuilderHolder, RouteModuleInstaller {

    private List<RouteBuilder> routeBuilders = new ArrayList<RouteBuilder>();
    
    private final static Logger logger = LoggerFactory.getLogger(RouterImpl.class);
    
    public List<RouteBuilder> getRegisteredRouteBuilders() {
        return Collections.unmodifiableList(this.routeBuilders);
    }

    @Override
    public RouteBuilder match(String url){
        RouteBuilder rb = new RouteBuilder(url);
        this.routeBuilders.add(rb);
        return rb;
    }
    
    @Override
    public void install(RouteModule module) {
        logger.info("Install route module : {}", module.getClass().getName());
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
