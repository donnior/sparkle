package org.agilej.sparkle.core.method;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.annotation.Async;
import org.agilej.sparkle.core.action.ActionMethod;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
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
     * otherwise will return a really result.
     *
     * @param actionMethod
     * @param controller
     * @param webRequest
     * @return
     */
    public Object execute(ActionMethod actionMethod, Object controller, WebRequest webRequest) {
        if(isAsyncActionDefinition(actionMethod)){
            logger.debug("Action is annotated with @Async, start processing as async request");

            boolean isCallableReturnType = actionMethod.getReturnType().equals(Callable.class);
            if(!isCallableReturnType){
                logger.debug("Async action is not wrapped in Callable, so wrap it first");
                return callableWrap(actionMethod, controller, webRequest);
            } else {
                return (Callable)new ActionExecutor(argumentResolverManager).invoke(actionMethod, controller, webRequest);
            }
        }

        return new ActionExecutor(this.argumentResolverManager).invoke(actionMethod, controller, webRequest);
    }

    private Callable<Object> callableWrap(final ActionMethod actionMethod, final Object controller,
                                          final WebRequest webRequest) {
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return new ActionExecutor(argumentResolverManager).invoke(actionMethod, controller, webRequest);
            }
        };
    }

    private boolean isAsyncActionDefinition(ActionMethod actionMethod) {
        return actionMethod.hasAnnotation(Async.class);
    }
}
