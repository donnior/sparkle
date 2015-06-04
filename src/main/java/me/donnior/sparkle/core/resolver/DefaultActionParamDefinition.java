package me.donnior.sparkle.core.resolver;

import java.lang.annotation.Annotation;
import java.util.List;

import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.core.ActionMethodParamDefinition;

import com.google.common.collect.Lists;

public class DefaultActionParamDefinition implements ActionMethodParamDefinition{
    
    private Class<?> paramType;
    private FList<Annotation> annotations;

    public DefaultActionParamDefinition(Class<?> paramType, List<Annotation> annotations){
        this.paramType = paramType;
        this.annotations = FLists.create(annotations);
    }

    @Override
    public Class<?> paramType() {
        return this.paramType;
    }

    public String paramName() {
        throw new UnsupportedOperationException("currently not allowed to get paramName");
    }
    
    @Override
    public boolean hasAnnotation(final Class<?> annotationType) {
        return this.annotations.any(new Predicate<Annotation>() {
            public boolean apply(Annotation a) {
                return a.annotationType().equals(annotationType);
            }
        });
    }

    @Override
    public Annotation getAnnotation(final Class<?> annotationType){
        return this.annotations.find(new Predicate<Annotation>() {
            @Override
            public boolean apply(Annotation e) {
                return e.annotationType().equals(annotationType);
            }
        });
    }

    @Override
    public List<Annotation> annotations() {
        return Lists.newArrayList(annotations);
    }
    
    public String toString() {
        return "ActionParamDefinition:[type=>"+this.paramType()+"]";
    }

}
