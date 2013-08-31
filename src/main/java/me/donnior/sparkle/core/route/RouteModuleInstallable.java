package me.donnior.sparkle.core.route;

import java.util.Collection;

import me.donnior.sparkle.route.RouteModule;


public interface RouteModuleInstallable {
    
    void install(RouteModule module);
    
    void install(Collection<RouteModule> modules);
    
}
