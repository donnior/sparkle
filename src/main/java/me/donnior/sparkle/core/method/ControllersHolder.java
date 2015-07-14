package me.donnior.sparkle.core.method;

import java.util.HashMap;
import java.util.Map;

import org.agilej.fava.FHashMap;
import org.agilej.fava.MConsumer;
import me.donnior.sparkle.exception.SparkleException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllersHolder implements ControllerClassResolver{
    
    private final static ControllersHolder instance = new ControllersHolder();
    
    private Map<String, Class<?>> controllers = new HashMap<String, Class<?>>();
    
    private final static Logger logger = LoggerFactory.getLogger(ControllersHolder.class);
    
    public Map<String, Class<?>> namedControllers(){
        return this.controllers;
    }
    
    public boolean containsController(String controllerName){
        return this.controllers.containsKey(controllerName);
    }

    /**
     * Must return a controller class or throw exception;
     * @param controllerName
     * @return
     */
    public Class<?> getControllerClass(String controllerName){
        Class controllerClass = this.controllers.get(controllerName);
        if (controllerClass == null) {
            throw new RuntimeException("Can't find any controller class with name : " + controllerName);
        }
        return controllerClass;
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
                    throw new SparkleException("Controller with name '" + controllerName + "' was duplicated");
                }
                controllers.put(controllerName, controllerClass);
//                logger.info("Found controller : {name => {}, class => {}}", controllerName, controllerClass.getName());
            }
        });
    }

    public static ControllersHolder getInstance() {
        return instance;
    }
    
}
