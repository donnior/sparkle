package me.donnior.sparkle.core;

public interface ControllerFactory {

    /**
     *
     * Get controller instance with given name or class. Should throw a runtime exception if can't get one.
     *
     * @param controllerName
     * @param controllerClass
     * @return
     */
    Object get(String controllerName, Class<?> controllerClass);
}
