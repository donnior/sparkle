package org.agilej.sparkle.core.support;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.agilej.sparkle.core.action.ControllerFactory;

/**
 * controller factory which use google guice as ioc container
 */
public class GuiceControllerFactory implements ControllerFactory {

    Injector injector;
    
    public GuiceControllerFactory(){
        this.injector = Guice.createInjector();
    }
    
    @Override
    public Object get(String controllerName, Class<?> controllerClass) {
        return this.injector.getInstance(controllerClass);
    }
}
