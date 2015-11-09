package org.agilej.sparkle.core.method;

import java.util.Map;

public interface ControllerClassResolver {
    
    Class<?> getControllerClass(String controllerName);
    
    void registeControllers(Map<String, Class<?>> controllersMap, boolean reset);
    
    void registeControllers(Map<String, Class<?>> controllersMap);

}
