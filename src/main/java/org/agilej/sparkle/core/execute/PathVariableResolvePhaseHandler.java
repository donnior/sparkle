package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.core.route.RouteInfo;

import java.util.Map;

public class PathVariableResolvePhaseHandler extends AbstractPhaseHandler {

    @Override
    public void handle(WebRequestExecutionContext context) {
        setPathVariablesToRequestAttribute(context.webRequest(), context.getRoute());
        forwardToNext(context);
    }

    private void setPathVariablesToRequestAttribute(WebRequest webRequest, RouteInfo rd) {
        Map<String, String> pathVariables = rd.pathVariables(webRequest.getPath());
        webRequest.setAttribute(WebRequest.REQ_ATTR_FOR_PATH_VARIABLES, pathVariables);
    }

}
