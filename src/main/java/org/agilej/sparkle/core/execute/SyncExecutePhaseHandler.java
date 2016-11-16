package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.ActionMethod;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.core.handler.ControllerActionExecutor;
import org.agilej.sparkle.core.route.RouteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncExecutePhaseHandler extends AbstractPhaseHandler {

    private final static Logger logger = LoggerFactory.getLogger(SyncExecutePhaseHandler.class);
    @Override
    public void handle(WebRequestExecutionContext context) {
        WebRequest webRequest = context.webRequest();
        Object tempResult = null;

        RouteInfo rd = context.getRoute();

        if (rd.isFunctionRoute()){
            logger.debug("Execute handler for functional route : {}", rd.getRouteFunction());
            tempResult = rd.getRouteFunction().apply(webRequest);
        } else {
            Object controller          = context.getController();
            ActionMethod actionMethod  = context.getActionMethod();
            Object[] params            = context.getArguments();
            tempResult = new ControllerActionExecutor().execute(actionMethod, controller, webRequest, params);
        }
        context.setActionResult(tempResult);
        forwardToNext(context);
    }


}
