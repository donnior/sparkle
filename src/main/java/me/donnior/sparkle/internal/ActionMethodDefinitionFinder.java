package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.reflections.Reflections;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

public class ActionMethodDefinitionFinder {

    public ActionMethodDefinition find(Class<? extends Object> clz, final String actionName) {
        
        Set<Method> methods = Reflections.getAllMethods(clz, Predicates.and(Reflections.withModifier(Modifier.PUBLIC),Reflections.withName(actionName)));
        if(methods.isEmpty()){
            throw new RuntimeException("can't find any action matched " + actionName);
        }
        if(methods.size() > 1){
            throw new RuntimeException("find more than actions with same name " + actionName);
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
                    
                    ActionParamDefinition apd = new ActionParamDefinition() {
                        
                        @Override
                        public Class<?> paramType() {
                            return type;
                        }
                        
                        @Override
                        public String paramName() {
                            throw new UnsupportedOperationException("currently not allowed to get paramName");
                        }
                        
                        @Override
                        public boolean hasAnnotation(Class annotationType) {
                            for(Annotation a : this.annotions()){
                                if(a.annotationType().equals(annotationType)){
                                    return true;
                                }
                            }
                            return false;
//                            return this.annotions().contains(annotation);
                        }
                        
                        @Override
                        public List<Annotation> annotions() {
                            return Lists.newArrayList(annotaions);
                        }
                    };
                    
                    apds.add(apd);
                }
                
                return apds;
            }
            
            @Override
            public Method method() {
                return method;
            }
            
            @Override
            public boolean hasAnnotation(Annotation annotation) {
                return this.method().getAnnotations().length > 0;
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
