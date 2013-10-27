package me.donnior.sparkle.engine;

public interface ControllerFactory {

    Object get(String controllerName, Class<?> controllerClass);
}
