package org.agilej.sparkle.core.route;

import java.util.Collection;

import org.agilej.sparkle.route.RouteModule;


public interface RouteModuleInstallable {
    
    void install(RouteModule module);
    
    void install(Collection<RouteModule> modules);
    
}
