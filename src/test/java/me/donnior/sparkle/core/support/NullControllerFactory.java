package me.donnior.sparkle.core.support;

import me.donnior.sparkle.core.ControllerFactory;

public class NullControllerFactory implements ControllerFactory {
    @Override
    public Object get(String controllerName, Class<?> controllerClass) {
        return null;
    }
};