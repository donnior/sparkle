package me.donnior.sparkle.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface ActionMethod {
    
    String actionName();
    
    Method method();
    
    List<ActionMethodParameter> paramDefinitions();
    
    List<Annotation> annotations();
    
    boolean hasAnnotation(Class<?> annotationType);
    
//    boolean isAsyncAction();
    
    public Class<?> getReturnType();

}
