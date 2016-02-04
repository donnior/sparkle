package org.agilej.sparkle.core.method;

import java.lang.reflect.Method;
import java.util.List;

import org.agilej.fava.Function;
import org.agilej.fava.util.FLists;
import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.ActionMethod;
import org.agilej.sparkle.core.ActionMethodParameter;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionExecutor {

    private ArgumentResolverManager argumentResolverManager ;

    private final static Logger logger = LoggerFactory.getLogger(ActionExecutor.class);

    public ActionExecutor(ArgumentResolverManager argumentResolverManager){
        this.argumentResolverManager = argumentResolverManager;
    }
    
    //TODO should refactored this params resolver, make it support multi resolvers so programmers can create their own
    // param resolver like param with class type 'Project'; so it should be List<ParamResolver>

    public Object invoke(ActionMethod actionMethod, Object controller, final WebRequest request) {

        logger.debug("Execute real action method {}#{}", controller.getClass().getSimpleName(), actionMethod.actionName());
        Method method = actionMethod.method();
        List<ActionMethodParameter> apds = actionMethod.parameters();
        
        Object[] params = FLists.create(apds).collect(new Function<ActionMethodParameter, Object>() {
            public Object apply(ActionMethodParameter apd) {
                return argumentResolverManager.resolve(apd, request);
            }
        }).toArray();
        
        return ReflectionUtil.invokeMethod(controller, method, params);
    }

}   
