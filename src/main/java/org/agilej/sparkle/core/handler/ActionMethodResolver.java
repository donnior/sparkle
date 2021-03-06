package org.agilej.sparkle.core.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.agilej.fava.util.FLists;
import org.agilej.sparkle.core.annotation.Singleton;
import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.mvc.ActionMethod;
import org.agilej.sparkle.mvc.ActionMethodParameter;
import org.agilej.sparkle.util.Tuple;
import org.agilej.sparkle.util.Tuple2;

import org.reflections.ReflectionUtils;

import com.google.common.base.Predicates;

@Singleton
public class ActionMethodResolver {

    static class ActionMethodImpl implements ActionMethod {

        private final Method method;
        private final String actionName;
        private final List<ActionMethodParameter> apds;
        private final Class<?> returnType;

        public ActionMethodImpl(Method method, String actionName){
            this.method = method;
            this.actionName = actionName;
            this.returnType = method.getReturnType();
            this.apds = FLists.$(method.getParameters()).map(DefaultActionMethodParameter::new);
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
        public List<ActionMethodParameter> parameters() {
            return this.apds;
        }

        @Override
        public List<Annotation> annotations() {
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

    private final Map<Tuple2<Class, String>, ActionMethod> cache =
            new ConcurrentHashMap<Tuple2<Class, String>, ActionMethod>();
    
    public ActionMethod find(Class clz, final String actionName) {
        Tuple2<Class, String> key = Tuple.of(clz, actionName);
        if(isCached(key)){
            return cache.get(key);
        }
        
        Set<Method> methods = ReflectionUtils.getAllMethods(clz, 
                Predicates.and(ReflectionUtils.withModifier(Modifier.PUBLIC),ReflectionUtils.withName(actionName)));

        SparkleException.throwIf(methods.isEmpty(),
                "Can't find any handler with name [%s] in class [%s]", actionName, clz.getSimpleName());
        SparkleException.throwIf(methods.size() > 1,
                "Found more than one actions with name [%s] in class [%s]", actionName, clz.getSimpleName());

        final Method method = methods.iterator().next();
        ActionMethod result = new ActionMethodImpl(method, actionName);
        cache.put(key, result);
        return result;
    }
    
    private boolean isCached(Tuple2<Class, String> key){
        return this.cache.containsKey(key);
    }

}
