package me.donnior.sparkle.engine;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.annotation.Async;
import me.donnior.sparkle.core.ActionMethod;
import me.donnior.sparkle.core.argument.ArgumentResolverManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Execute controller action, if the action is asynchronous it will not call the real action and
 * return a callable object for further async processing
 */
public class ControllerExecutor {

    private final ArgumentResolverManager argumentResolverManager;

    private final static Logger logger = LoggerFactory.getLogger(ControllerExecutor.class);

    public ControllerExecutor(ArgumentResolverManager argumentResolverManager) {
        this.argumentResolverManager = argumentResolverManager;
    }

    /**
     *
     * execute controller's action. If the action need be execute asynchronously, returns a callable object;
     * otherwise will return a realy result.
     *
     * @param actionMethod
     * @param controller
     * @param webRequest
     * @return
     */
    public Object execute(ActionMethod actionMethod, Object controller, WebRequest webRequest) {
        if(isAsyncActionDefinition(actionMethod)){
            logger.debug("Action is annotated with @Async, start processing as async request");

            boolean isCallableReturnType = actionMethod.getReturnType().getClass().equals(Callable.class);
            Callable<Object> c = null;
            if(!isCallableReturnType){
                return new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        return new ActionExecutor(argumentResolverManager).invoke(actionMethod, controller, webRequest);
                    }
                };
            } else {
                return (Callable)new ActionExecutor(argumentResolverManager).invoke(actionMethod, controller, webRequest);
            }
        }

        return new ActionExecutor(this.argumentResolverManager).invoke(actionMethod, controller, webRequest);
    }

    private boolean isAsyncActionDefinition(ActionMethod actionMethod) {
        return actionMethod.hasAnnotation(Async.class);
    }
}
