package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.ApplicationController;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethod;
import org.agilej.sparkle.core.action.ActionMethodResolver;
import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.core.method.ControllerClassResolver;
import org.agilej.sparkle.core.method.ControllerExecutor;
import org.agilej.sparkle.core.method.ControllerInstanceResolver;
import org.agilej.sparkle.core.route.RouteInfo;
import org.agilej.sparkle.exception.SparkleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FirstAttemptExecutePhaseHandler extends AbstractPhaseHandler {

    private ActionMethodResolver actionMethodResolver;
    private ArgumentResolverManager argumentResolverManager;

    private ControllerInstanceResolver controllerInstanceResolver;

    private final static Logger logger = LoggerFactory.getLogger(FirstAttemptExecutePhaseHandler.class);
    @Override
    public void handle(WebRequestExecutionContext context) {
        WebRequest webRequest = context.webRequest();
        Object tempResult = null;
        Object controller = null;
        ActionMethod actionMethod = null;
        RouteInfo rd = context.getRoute();

        if (rd.isFunctionRoute()){
            logger.debug("Execute action for functional route : {}", rd.getRouteFunction());
            tempResult = rd.getRouteFunction().apply(webRequest);
        } else {
            controller = getControllerInstanceForRoute(rd);

            presetControllerIfNeed(webRequest, controller);
            actionMethod = this.actionMethodResolver.find(controller.getClass(), rd.getActionName());
            tempResult = new ControllerExecutor(this.argumentResolverManager).execute(actionMethod, controller, webRequest);
        }
        context.setController(controller);
        context.setControllerActionResult(tempResult);
        context.setActionMethod(actionMethod);
        forwardToNext(context);
    }


    private Object getControllerInstanceForRoute(RouteInfo rd){
        return  this.controllerInstanceResolver.get(rd);
    }

    private void presetControllerIfNeed(WebRequest webRequest, Object controller) {
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(webRequest);
            ((ApplicationController)controller).setResponse(webRequest.getWebResponse());
        }
    }

    public void setActionMethodResolver(ActionMethodResolver actionMethodResolver) {
        this.actionMethodResolver = actionMethodResolver;
    }

    public void setArgumentResolverManager(ArgumentResolverManager argumentResolverManager) {
        this.argumentResolverManager = argumentResolverManager;
    }

    public void setControllerInstanceResolver(ControllerInstanceResolver controllerInstanceResolver) {
        this.controllerInstanceResolver = controllerInstanceResolver;
    }
}
