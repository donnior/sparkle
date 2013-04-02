package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface ActionMethodDefinition {
    
    String actionName();
    
    Method method();
    
    List<ActionParamDefinition> paramDefinitions();
    
    List<Annotation> annotions();
    
    boolean hasAnnotation(Annotation annotation);

}
