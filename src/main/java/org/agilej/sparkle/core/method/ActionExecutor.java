package org.agilej.sparkle.core.method;

import java.lang.reflect.Method;
import java.util.List;

import org.agilej.fava.Function;
import org.agilej.fava.util.FLists;
import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethod;
import org.agilej.sparkle.core.action.ActionMethodParameter;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionExecutor {

    private ArgumentResolverManager argumentResolverManager ;

    private final static Logger logger = LoggerFactory.getLogger(ActionExecutor.class);

    public ActionExecutor(ArgumentResolverManager argumentResolverManager){
        this.argumentResolverManager = argumentResolverManager;
    }
    
    public Object invoke(ActionMethod actionMethod, Object controller, final WebRequest request) {

        logger.debug("Execute real action method {}#{}", controller.getClass().getSimpleName(), actionMethod.actionName());
        Method method = actionMethod.method();
        List<ActionMethodParameter> amps = actionMethod.parameters();
        
        Object[] params = FLists.create(amps).collect(new Function<ActionMethodParameter, Object>() {
            public Object apply(ActionMethodParameter amp) {
                return argumentResolverManager.resolve(amp, request);
            }
        }).toArray();
        
        return ReflectionUtil.invokeMethod(controller, method, params);
    }

}   
