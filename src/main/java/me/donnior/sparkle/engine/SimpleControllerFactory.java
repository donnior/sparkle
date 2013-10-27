package me.donnior.sparkle.engine;

import me.donnior.reflection.ReflectionUtil;

public class SimpleControllerFactory implements ControllerFactory{

    @Override
    public Object get(String controllerName, Class<?> controllerClass) {
        if(controllerClass != null){
            return ReflectionUtil.initialize(controllerClass);
        }
        return null;
    }
}
