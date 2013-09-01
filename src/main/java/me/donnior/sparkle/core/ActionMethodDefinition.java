package me.donnior.sparkle.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface ActionMethodDefinition {
    
    String actionName();
    
    Method method();
    
    List<ActionParamDefinition> paramDefinitions();
    
    List<Annotation> annotions();
    
    boolean hasAnnotation(Class<?> annotationType);
    
//    boolean isAsyncAction();

}
