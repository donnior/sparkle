package me.donnior.sparkle.core.resolver;

import java.util.Map;

public interface ControllerClassResolver {
    
    Class<?> getControllerClass(String controllerName);
    
    void registeControllers(Map<String, Class<?>> controllersMap, boolean reset);
    
    void registeControllers(Map<String, Class<?>> controllersMap);

}
