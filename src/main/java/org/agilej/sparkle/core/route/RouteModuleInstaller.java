package org.agilej.sparkle.core.route;

import java.util.Collection;

import org.agilej.sparkle.route.RouteModule;


public interface RouteModuleInstaller {
    
    void install(RouteModule module);
    
    default void install(Collection<RouteModule> modules) {
        modules.forEach(this::install);
    }
    
}
