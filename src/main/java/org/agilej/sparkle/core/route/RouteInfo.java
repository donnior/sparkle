package org.agilej.sparkle.core.route;

import org.agilej.sparkle.WebRequest;
import org.agilej.jsonty.JSONModel;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface RouteInfo {

    boolean isFunctionRoute();

    Function<WebRequest, JSONModel> getRouteFunction();

    String getActionName();

    String getControllerName();

    List<String> getPathVariables();

    Map<String, String > pathVariables(String path);
}
