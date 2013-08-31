package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import me.donnior.sparkle.exception.SparkleException;

import org.reflections.ReflectionUtils;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

public class ActionMethodDefinitionFinder {

    static class DefaultActionMethodDefinition implements ActionMethodDefinition{

        private final Method method;
        private final String actionName;
        private final List<ActionParamDefinition> apds;

        public DefaultActionMethodDefinition(Method method, String actionName){
            this.method = method;
            this.actionName = actionName;
            
            final Class<?>[] paramTypes = method.getParameterTypes();
            final Annotation[][] ans = method.getParameterAnnotations();
          
            List<ActionParamDefinition> apds = Lists.newArrayList();
            for(int i=0; i<paramTypes.length; i++){
                final Class<?> type = paramTypes[i];
                final Annotation[] annotaions = ans[i];
                ActionParamDefinition apd = new DefaulActionParamDefinition(type, Arrays.asList(annotaions));
                apds.add(apd);
            }
            
            this.apds = apds;
            
        }
        
        @Override
        public String actionName() {
            return this.actionName;
        }

        @Override
        public Method method() {
            return this.method;
        }

        @Override
        public List<ActionParamDefinition> paramDefinitions() {
            return this.apds;
        }

        @Override
        public List<Annotation> annotions() {
            return Arrays.asList(this.method().getAnnotations());
        }

        @SuppressWarnings({ "unchecked", "rawtypes" })
        @Override
        public boolean hasAnnotation(Class annotationType) {
            return this.method().isAnnotationPresent(annotationType);
        }
        
    }
    
    //TODO add cache here
    public ActionMethodDefinition find(Class<? extends Object> clz, final String actionName) {
        
        Set<Method> methods = ReflectionUtils.getAllMethods(clz, Predicates.and(ReflectionUtils.withModifier(Modifier.PUBLIC),ReflectionUtils.withName(actionName)));
        if(methods.isEmpty()){
            throw new SparkleException("can't find any action matched " + actionName);
        }
        if(methods.size() > 1){
            throw new SparkleException("find more than actions with same name " + actionName);
        }
        final Method method = methods.iterator().next();
        return new DefaultActionMethodDefinition(method, actionName);
    }

}
