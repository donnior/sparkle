package me.donnior.sparkle.core.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.ActionMethodParamDefinition;
import me.donnior.sparkle.exception.SparkleException;
import me.donnior.sparkle.util.Singleton;
import me.donnior.sparkle.util.Tuple;
import me.donnior.sparkle.util.Tuple2;

import org.reflections.ReflectionUtils;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

@Singleton
public class ActionMethodDefinitionFinder {

    static class DefaultActionMethodDefinition implements ActionMethodDefinition{

        private final Method method;
        private final String actionName;
        private final List<ActionMethodParamDefinition> apds;
        private final Class<?> returnType;

        public DefaultActionMethodDefinition(Method method, String actionName){
            this.method = method;
            this.actionName = actionName;
            this.returnType = method.getReturnType();
            
            final Class<?>[] paramTypes = method.getParameterTypes();
            final Annotation[][] ans = method.getParameterAnnotations();
          
            List<ActionMethodParamDefinition> apds = Lists.newArrayList();
            for(int i=0; i<paramTypes.length; i++){
                final Class<?> type = paramTypes[i];
                final Annotation[] annotaions = ans[i];
                ActionMethodParamDefinition apd = new DefaulActionParamDefinition(type, Arrays.asList(annotaions));
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
        public List<ActionMethodParamDefinition> paramDefinitions() {
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
        
        @Override
        public Class<?> getReturnType() {
            return returnType;
        }
        
    }
    
//    private static class ActionMethodKey {
//        
//        private Class clz;
//        private String actionName;
//
//        public ActionMethodKey(Class clz, String actionName) {
//            this.clz = clz;
//            this.actionName = actionName;
//        }
//        
//        @Override
//        public int hashCode() {
//            return Objects.hashCode(this.clz, this.actionName);
//        }
//        
//        @Override
//        public boolean equals(Object obj) {
//            if(obj == null) { return false;}
//            if(!(obj instanceof ActionMethodKey)){ return false; }
//            ActionMethodKey other = (ActionMethodKey)obj;
//            return this.clz.equals(other.clz) && this.actionName.equals(other.actionName);
//        }
//        
//    }
    
    //TODO remove static, make this ActionMethodDefinitionFinder singleton in SparkleEngin
    private  Map<Tuple2<Class, String>, ActionMethodDefinition> cache = 
            new ConcurrentHashMap<Tuple2<Class, String>, ActionMethodDefinition>(); 
    
    public ActionMethodDefinition find(Class clz, final String actionName) {
        Tuple2<Class, String> key = Tuple.of(clz, actionName);
        if(isCached(key)){
            return cache.get(key);
        }
        
        Set<Method> methods = ReflectionUtils.getAllMethods(clz, 
                Predicates.and(ReflectionUtils.withModifier(Modifier.PUBLIC),ReflectionUtils.withName(actionName)));
        if(methods.isEmpty()){
            throw new SparkleException("can't find any action matched " + actionName);
        }
        if(methods.size() > 1){
            throw new SparkleException("find more than actions with same name " + actionName);
        }
        final Method method = methods.iterator().next();
        ActionMethodDefinition result = new DefaultActionMethodDefinition(method, actionName);
        cache.put(key, result);
        return result;
    }
    
    private boolean isCached(Tuple2<Class, String> key){
        return this.cache.containsKey(key);
    }

}
