package me.donnior.sparkle.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface ActionMethodDefinition {
    
    String actionName();
    
    Method method();
    
    List<ActionMethodParamDefinition> paramDefinitions();
    
    List<Annotation> annotations();
    
    boolean hasAnnotation(Class<?> annotationType);
    
//    boolean isAsyncAction();
    
    public Class<?> getReturnType();

}
