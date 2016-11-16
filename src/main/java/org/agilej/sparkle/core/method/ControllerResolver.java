package org.agilej.sparkle.core.method;

import org.agilej.sparkle.core.route.RouteInfo;

/**
 * get(create instance) controller object for given route, generally it will use {@link ControllerClassResolver} to get
 * controller class first, then use {@link org.agilej.sparkle.core.action.ControllerFactory} to get controller instance
 */
public interface ControllerResolver {

    /**
     * resolve controller instance for given route info, must return object or throw exception, can't be null
     *
     * @param routeInfo
     * @return
     */
    Object get(RouteInfo routeInfo);

}
