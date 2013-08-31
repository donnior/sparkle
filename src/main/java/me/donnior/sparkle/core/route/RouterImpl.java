package me.donnior.sparkle.core.route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import me.donnior.sparkle.route.RouteModule;
import me.donnior.sparkle.route.Router;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouterImpl implements Router, RouteBuilderHolder, RouteModuleInstallable {

    private static RouterImpl instance = new RouterImpl();
    
    private List<RouteBuilder> routeBuilders = new ArrayList<RouteBuilder>();
    
    private final static Logger logger = LoggerFactory.getLogger(RouterImpl.class);
    
    public static RouterImpl getInstance() {
        return instance;
    }
    
    public List<RouteBuilder> getRegisteredRouteBuilders() {
        return Collections.unmodifiableList(this.routeBuilders);
    }
    
    public RouteBuilder match(String url){
        RouteBuilder rb = new RouteBuilder(url);
        this.routeBuilders.add(rb);
        return rb;
    }
    
    @Override
    public void install(RouteModule module) {
        logger.info("Install route module : {}", module.getClass() );
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
