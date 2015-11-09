package org.agilej.sparkle.engine;

import java.util.Set;

import org.agilej.sparkle.config.Application;
import org.agilej.sparkle.exception.SparkleException;

import org.reflections.Reflections;

public class ApplicationConfigScanner {

    public Class<? extends Application> scan() {
        return this.scan("");
    }
    
    public Class<? extends Application> scan(String pkg) {
        Reflections reflections = new Reflections(pkg);
        Set<Class<? extends Application>> applications = reflections.getSubTypesOf(Application.class);
        if(applications.size()>1){
            throw new SparkleException("found more than one ApplicationConfig class");
        }
        if(applications.isEmpty()){
            return null;
        }
        return applications.iterator().next();
    }

}
