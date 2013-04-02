package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ActionParamDefinition {

    String paramName();
    
    Class<?> paramType();
    
    List<Annotation> annotions();
    
    boolean hasAnnotation(Class<?> annotationType);
    
}
