package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;
import java.util.List;

import com.google.common.collect.Lists;

public class DefaulActionParamDefinition implements ActionParamDefinition{
    
    private Class<?> paramType;
    private List<Annotation> annotaions;

    public DefaulActionParamDefinition(Class<?> paramType, List<Annotation> annotations){
        this.paramType = paramType;
        this.annotaions = annotations;
    }

    @Override
    public Class<?> paramType() {
        return this.paramType;
    }

    public String paramName() {
        throw new UnsupportedOperationException("currently not allowed to get paramName");
    }
    
    @Override
    public boolean hasAnnotation(Class<?> annotationType) {
        for(Annotation a : this.annotions()){
            if(a.annotationType().equals(annotationType)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public List<Annotation> annotions() {
        return Lists.newArrayList(annotaions);
    }
    
    public String toString() {
        return "ActionParamDefinition:[type=>"+this.paramType()+"]";
    };

}
