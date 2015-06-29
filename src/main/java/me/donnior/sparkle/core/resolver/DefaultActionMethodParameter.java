package me.donnior.sparkle.core.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.core.ActionMethodParameter;

import com.google.common.collect.Lists;

public class DefaultActionMethodParameter implements ActionMethodParameter {

    private String name;
    private Class<?> paramType;
    private FList<Annotation> annotations;
//    private Parameter parameter;

    public DefaultActionMethodParameter(Class<?> paramType, List<Annotation> annotations){
        this(null, paramType, annotations);
    }

    public DefaultActionMethodParameter(String name, Class<?> paramType, List<Annotation> annotations){
        this.name = name;
        this.paramType = paramType;
        this.annotations = FLists.create(annotations);
    }


    public DefaultActionMethodParameter(Parameter parameter){
        this(parameter.getName(), parameter.getType(), Arrays.asList(parameter.getAnnotations()));
//        this.parameter = parameter;
    }

    @Override
    public Class<?> paramType() {
        return this.paramType;
    }

    public String paramName() {
//        return this.name;
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
        return "ActionMethodParameter:[type=>"+this.paramType()+"]";
    }

}
