package org.agilej.sparkle.mvc;

import org.agilej.sparkle.core.annotation.Singleton;
import org.agilej.sparkle.core.annotation.UserConfigurable;

/**
 * factory for controller instance
 */
@UserConfigurable
@Singleton
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
