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

    private final static Logger logger = LoggerFactory.getLogger(FirstAttemptExecutePhaseHandler.class);
    @Override
    public void handle(WebRequestExecutionContext context) {
        WebRequest webRequest = context.webRequest();
        Object tempResult = null;

        RouteInfo rd = context.getRoute();

        if (rd.isFunctionRoute()){
            logger.debug("Execute action for functional route : {}", rd.getRouteFunction());
            tempResult = rd.getRouteFunction().apply(webRequest);
        } else {
            Object controller          = context.getController();
            ActionMethod actionMethod  = context.getActionMethod();
            Object[] params            = context.getArguments();
            tempResult = new ControllerExecutor().execute(actionMethod, controller, webRequest, params);
        }
        context.setControllerActionResult(tempResult);
        forwardToNext(context);
    }


}
