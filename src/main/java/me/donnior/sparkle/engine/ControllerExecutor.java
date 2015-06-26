package me.donnior.sparkle.engine;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.annotation.Async;
import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.resolver.ArgumentResolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;


public class ControllerExecutor {

    private final ArgumentResolverManager argumentResolverManager;

    private final static Logger logger = LoggerFactory.getLogger(ControllerExecutor.class);

    public ControllerExecutor(ArgumentResolverManager argumentResolverManager) {
        this.argumentResolverManager = argumentResolverManager;
    }


    public Object execute(ActionMethodDefinition adf, Object controller, WebRequest webRequest) {
        if(isAsyncActionDefinition(adf)){
            logger.info("Action is annotated with @Async, start processing as async request");

            boolean isCallableReturnType = adf.getReturnType().getClass().equals(Callable.class);
            Callable<Object> c = null;
            if(!isCallableReturnType){
                return new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        return new ActionExecutor(argumentResolverManager).invoke(adf, controller, webRequest);
                    }
                };
            } else {
                return (Callable)new ActionExecutor(argumentResolverManager).invoke(adf, controller, webRequest);
            }
        }

        return new ActionExecutor(this.argumentResolverManager).invoke(adf, controller, webRequest);
    }

    private boolean isAsyncActionDefinition(ActionMethodDefinition adf) {
        return adf.hasAnnotation(Async.class);
    }
}
