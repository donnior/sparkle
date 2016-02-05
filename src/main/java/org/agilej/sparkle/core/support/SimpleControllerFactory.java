package org.agilej.sparkle.core.support;

import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.core.action.ControllerFactory;

/**
 * Simple controller factory which just create controller instance use reflection, don't support IoC.
 *
 * <p>
 *     If you want a IoC controller factory, consider {@link GuiceControllerFactory}, or create implementation yourself.
 * </p>
 *
 */
public class SimpleControllerFactory implements ControllerFactory {

    @Override
    public Object get(String controllerName, Class<?> controllerClass) {
        if(controllerClass != null){
            return ReflectionUtil.initialize(controllerClass);
        }
        return null;
    }
}
