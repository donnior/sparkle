package me.donnior.sparkle.servlet;

public interface ControllerFactory {

    Object get(String controllerName, Class<?> controllerClass);
}
