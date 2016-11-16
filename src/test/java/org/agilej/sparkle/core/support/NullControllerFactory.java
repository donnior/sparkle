package org.agilej.sparkle.core.support;

import org.agilej.sparkle.mvc.ControllerFactory;

public class NullControllerFactory implements ControllerFactory {
    @Override
    public Object get(String controllerName, Class<?> controllerClass) {
        return null;
    }
};