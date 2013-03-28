package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ActionParamDefinition {

    public String paramName();
    
    public Class<?> paramType();
    
    public List<Annotation> annotions();
    
    public boolean hasAnnotation(Class<?> annotationType);
    
}
