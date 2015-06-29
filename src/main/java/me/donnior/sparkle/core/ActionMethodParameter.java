package me.donnior.sparkle.core;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ActionMethodParameter {

    String paramName();
    
    Class<?> paramType();
    
    List<Annotation> annotations();
    
    boolean hasAnnotation(Class<?> annotationType);

    Annotation getAnnotation(Class<?> annotationType);
    
}
