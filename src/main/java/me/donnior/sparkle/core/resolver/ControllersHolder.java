package me.donnior.sparkle.core.resolver;

import java.util.HashMap;
import java.util.Map;

import me.donnior.fava.FHashMap;
import me.donnior.fava.MConsumer;
import me.donnior.sparkle.exception.SparkleException;

public class ControllersHolder implements ControllerClassResolver{
    
    private final static ControllersHolder instance = new ControllersHolder();
    
    private Map<String, Class<?>> controllers = new HashMap<String, Class<?>>();
    
    public Map<String, Class<?>> namedControllers(){
        return this.controllers;
    }
    
    public boolean containsController(String controllerName){
        return this.controllers.containsKey(controllerName);
    }
    
    public Class<?> getControllerClass(String controllerName){
        return this.controllers.get(controllerName);
    }

    public void registeControllers(Map<String, Class<?>> controllersMap) {
        this.registeControllers(controllersMap, false);
    }
    
    public void registeControllers(Map<String, Class<?>> controllersMap, boolean reset) {
        if(reset){
            this.controllers.clear();
        }
        new FHashMap<String, Class<?>>(controllersMap).each(new MConsumer<String, Class<?>>() {
            public void apply(String controllerName, Class<?> controllerClass) {
                if(containsController(controllerName)){
                    throw new SparkleException("Controller with name " + controllerName + " was duplicated");
                }
                controllers.put(controllerName, controllerClass);
            }
        });
    }

    public static ControllersHolder getInstance() {
        return instance;
    }
    
}
