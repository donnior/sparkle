package org.agilej.sparkle.core.engine;

import java.util.Set;

import org.agilej.sparkle.config.Application;
import org.agilej.sparkle.exception.SparkleException;

import org.reflections.Reflections;

public class ApplicationConfigScanner {
    
    public Class<? extends Application> scan(String pkg) {
        Reflections reflections = new Reflections(pkg);
        Set<Class<? extends Application>> applications = reflections.getSubTypesOf(Application.class);
        if(applications.size() > 1){
            throw new SparkleException("found more than one Application classes");
        }
        if(applications.isEmpty()){
            return null;
        }
        return applications.iterator().next();
    }

}
