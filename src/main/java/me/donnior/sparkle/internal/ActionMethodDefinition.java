package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

public interface ActionMethodDefinition {
    
    public String actionName();
    
    public Method method();
    
    public List<ActionParamDefinition> paramDefinitions();
    
    public List<Annotation> annotions();
    
    public boolean hasAnnotation(Annotation annotation);

}
