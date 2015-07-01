package me.donnior.sparkle.core.support;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.core.ControllerFactory;

public class SimpleControllerFactory implements ControllerFactory {

    @Override
    public Object get(String controllerName, Class<?> controllerClass) {
        if(controllerClass != null){
            return ReflectionUtil.initialize(controllerClass);
        }
        return null;
    }
}
