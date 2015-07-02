package me.donnior.sparkle.core.resolver;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import me.donnior.sparkle.core.ActionMethod;
import me.donnior.sparkle.core.ActionMethodParameter;
import me.donnior.sparkle.exception.SparkleException;
import me.donnior.sparkle.util.Singleton;
import me.donnior.sparkle.util.Tuple;
import me.donnior.sparkle.util.Tuple2;

import org.reflections.ReflectionUtils;

import com.google.common.base.Predicates;
import com.google.common.collect.Lists;

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

            List<ActionMethodParameter> apds = Lists.newArrayList();
//            final Class<?>[] paramTypes = method.getParameterTypes();
//            final Annotation[][] ans = method.getParameterAnnotations();
//
//            for(int i=0; i<paramTypes.length; i++){
//                final Class<?> type = paramTypes[i];
//                final Annotation[] annotations = ans[i];
//                ActionMethodParameter apd = new DefaultActionMethodParameter(type, Arrays.asList(annotations));
//                apds.add(apd);
//            }

            for (Parameter p : method.getParameters()){
                apds.add(new DefaultActionMethodParameter(p));
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
        if(methods.isEmpty()){
            throw new SparkleException("Can't find any action with name : " + actionName + " in class : " + clz);
        }
        if(methods.size() > 1){
            throw new SparkleException("Found more than one actions with name : " + actionName + " in class : " + clz);
        }
        final Method method = methods.iterator().next();
        ActionMethod result = new ActionMethodImpl(method, actionName);
        cache.put(key, result);
        return result;
    }
    
    private boolean isCached(Tuple2<Class, String> key){
        return this.cache.containsKey(key);
    }

}
