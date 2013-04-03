package me.donnior.sparkle.internal;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import me.donnior.sparkle.ApplicationController;
import me.donnior.sparkle.annotation.Controller;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ControllerScanner {
    
    private final static Logger logger = LoggerFactory.getLogger(ControllerScanner.class);

    private Map<String, Class<?>> controllers = new HashMap<String, Class<?>>();
    
    public Map<String, Class<?>> scanControllers(String pkg){
        logger.info("begin scanning controllers under package {}", pkg);
        Reflections reflections = new Reflections(pkg);
        Set<Class<?>> annotated = reflections.getTypesAnnotatedWith(Controller.class);
        for(Class<?> clz : annotated){
            Controller controller = (Controller)clz.getAnnotation(Controller.class);
            this.controllers.put(controller.value(), clz);
            logger.debug("founded annotated controller {} with class {} ", controller.value(), clz.getName());
        }
        Set<Class<? extends ApplicationController>> inherited = reflections.getSubTypesOf(ApplicationController.class);
        for(Class<?> clz : inherited){
            boolean controllerIsBothAnnotatedAndInherited = annotated.contains(clz);
            if(controllerIsBothAnnotatedAndInherited){
                continue; //since already been processed above
            }
            String controllerName = clz.getSimpleName();
            this.controllers.put(controllerName, clz);
            logger.debug("founded inherited controller {} with class {} ", clz.getSimpleName(), clz.getName());
        }
        
        return this.controllers;
    }
    
}
