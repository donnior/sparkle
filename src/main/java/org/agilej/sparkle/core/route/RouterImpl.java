package org.agilej.sparkle.core.route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.agilej.sparkle.route.RouteModule;
import org.agilej.sparkle.route.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouterImpl implements Router, RouteBuilderHolder, RouteModuleInstaller {

    private final static Logger logger = LoggerFactory.getLogger(RouterImpl.class);

    private List<RouteBuilder> routeBuilders = new ArrayList<>();

    @Override
    public  List<RouteBuilder> getRegisteredRouteBuilders() {
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
    
    public void clear(){
        this.routeBuilders.clear();
    }
    
}
