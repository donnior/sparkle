package me.donnior.sparkle.core.support;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.core.ControllerFactory;

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
