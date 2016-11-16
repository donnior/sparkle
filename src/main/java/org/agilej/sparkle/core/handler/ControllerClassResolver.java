package org.agilej.sparkle.core.handler;

import org.agilej.sparkle.core.annotation.Singleton;

import java.util.Map;

@Singleton
public interface ControllerClassResolver {
    
    Class<?> getControllerClass(String controllerName);
    
    void registerControllers(Map<String, Class<?>> controllersMap, boolean reset);
    
    void registerControllers(Map<String, Class<?>> controllersMap);

}
