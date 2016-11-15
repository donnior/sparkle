package org.agilej.sparkle.core.method;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.annotation.Async;
import org.agilej.sparkle.core.action.ActionMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;

/**
 * Execute controller action, if the action is asynchronous it will not call the real action and
 * return (or wrap) a callable object for further async processing
 */
public class ControllerExecutor {

    private final static Logger logger = LoggerFactory.getLogger(ControllerExecutor.class);

    public ControllerExecutor() {}

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
    public Object execute(ActionMethod actionMethod, Object controller, WebRequest webRequest, Object[] params) {
        if(isAsyncActionDefinition(actionMethod)){
            logger.debug("Action is annotated with @Async, start processing as async request");

            boolean isCallableReturnType = actionMethod.getReturnType().equals(Callable.class);
            if(!isCallableReturnType){
                logger.debug("Wrap async action as Callable");
                return callableWrap(actionMethod, controller, webRequest, params);
            } else {
                return (Callable)new ActionExecutor().invoke(actionMethod, controller, webRequest, params);
            }
        }

        return new ActionExecutor().invoke(actionMethod, controller, webRequest, params);
    }

    private Callable<Object> callableWrap(final ActionMethod actionMethod, final Object controller,
                                          final WebRequest webRequest, final Object[] params) {
        return new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return new ActionExecutor().invoke(actionMethod, controller, webRequest, params);
            }
        };
    }

    private boolean isAsyncActionDefinition(ActionMethod actionMethod) {
        return actionMethod.hasAnnotation(Async.class);
    }
}
