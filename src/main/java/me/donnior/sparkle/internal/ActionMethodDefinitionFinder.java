package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import me.donnior.sparkle.exception.SparkleException;

import org.reflections.Reflections;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

public class ActionMethodDefinitionFinder {

    public ActionMethodDefinition find(Class<? extends Object> clz, final String actionName) {
        
        Set<Method> methods = Reflections.getAllMethods(clz, Predicates.and(Reflections.withModifier(Modifier.PUBLIC),Reflections.withName(actionName)));
        if(methods.isEmpty()){
            throw new SparkleException("can't find any action matched " + actionName);
        }
        if(methods.size() > 1){
            throw new SparkleException("find more than actions with same name " + actionName);
        }
        final Method method = methods.iterator().next();        
        return new ActionMethodDefinition() {
            
            @Override
            public List<ActionParamDefinition> paramDefinitions() {
                final Class<?>[] paramTypes = method.getParameterTypes();
                final Annotation[][] ans = method.getParameterAnnotations();
              
                List<ActionParamDefinition> apds = Lists.newArrayList();
                for(int i=0; i<paramTypes.length; i++){
                    final Class<?> type = paramTypes[i];
                    final Annotation[] annotaions = ans[i];
                    ActionParamDefinition apd = new DefaulActionParamDefinition(type, Arrays.asList(annotaions));
                    apds.add(apd);
                }
                
                return apds;
            }
            
            @Override
            public Method method() {
                return method;
            }
            
            @SuppressWarnings({ "rawtypes", "unchecked" })
            @Override
            public boolean hasAnnotation(Class annotationType) {
                return this.method().isAnnotationPresent(annotationType);
            }
            
            @Override
            public List<Annotation> annotions() {
                return Arrays.asList(this.method().getAnnotations());
            }
            
            @Override
            public String actionName() {
                return actionName;
            }
        };
        
    }

}
