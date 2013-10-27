package me.donnior.sparkle.engine;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class GuiceControllerFactory implements ControllerFactory{

    Injector injector;
    
    public GuiceControllerFactory(){
        this.injector = Guice.createInjector();
    }
    
    @Override
    public Object get(String controllerName, Class<?> controllerClass) {
        return this.injector.getInstance(controllerClass);
    }
}
