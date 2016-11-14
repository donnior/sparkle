package org.agilej.sparkle.core.method;

import org.agilej.sparkle.core.route.RouteInfo;

public interface ControllerInstanceResolver {

    Object get(RouteInfo routeInfo);

}
