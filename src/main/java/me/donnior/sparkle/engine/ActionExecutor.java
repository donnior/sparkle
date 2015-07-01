package me.donnior.sparkle.engine;

import java.lang.reflect.Method;
import java.util.List;

import me.donnior.fava.Function;
import me.donnior.fava.util.FLists;
import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethod;
import me.donnior.sparkle.core.ActionMethodParameter;
import me.donnior.sparkle.core.resolver.ArgumentResolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActionExecutor {

    private ArgumentResolverManager argumentResolverManager ;

    private final static Logger logger = LoggerFactory.getLogger(ActionExecutor.class);

    public ActionExecutor(ArgumentResolverManager argumentResolverManager){
        this.argumentResolverManager = argumentResolverManager;
    }
    
    //TODO should refactord this params resolver, make it support multi resolvers so programmers can create their own
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
