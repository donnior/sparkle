package me.donnior.sparkle.route;

import java.util.Collection;


public interface RouteModuleInstallable {
    
    void install(RouteModule module);
    
    void install(Collection<RouteModule> modules);
    
}
