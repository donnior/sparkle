package org.agilej.sparkle.core.method;

import java.lang.reflect.Method;

import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionExecutor {

    private final static Logger logger = LoggerFactory.getLogger(ActionExecutor.class);

    public ActionExecutor(){
    }
    
    public Object invoke(ActionMethod actionMethod, Object controller, final WebRequest request, Object[] params) {

        logger.debug("Execute action method {}#{}", controller.getClass().getSimpleName(), actionMethod.actionName());
        Method method = actionMethod.method();

        return ReflectionUtil.invokeMethod(controller, method, params);
    }

}   
