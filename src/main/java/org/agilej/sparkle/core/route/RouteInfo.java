package org.agilej.sparkle.core.route;

import org.agilej.sparkle.WebRequest;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface RouteInfo {

    boolean isFunctionRoute();

    Function<WebRequest, Object> getRouteFunction();

    String getActionName();

    String getControllerName();

    List<String> getPathVariableNames();

    Map<String, String > pathVariables(String path);
}
