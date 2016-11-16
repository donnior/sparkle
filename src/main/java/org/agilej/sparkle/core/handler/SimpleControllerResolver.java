package org.agilej.sparkle.core.handler;

import org.agilej.sparkle.mvc.ControllerFactory;
import org.agilej.sparkle.core.route.RouteInfo;
import org.agilej.sparkle.exception.SparkleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleControllerResolver implements ControllerResolver {

    private final static Logger logger = LoggerFactory.getLogger(SimpleControllerResolver.class);

    private ControllerClassResolver controllerClassResolver;
    private ControllerFactory controllerFactory;

    @Override
    public Object get(RouteInfo routeInfo) {
        String controllerName = routeInfo.getControllerName();
        final Class<?> controllerClass = this.controllerClassResolver.getControllerClass(controllerName);
        final Object controller  = this.controllerFactory.get(controllerName, controllerClass);
        if(controller == null){
            logger.error("Can't get controller instance with name : {} and class : {}", controllerName, controllerClass);
            throw new SparkleException("Can't get controller instance with name : %s and class : %s",
                    controllerName, controllerClass);
        }
        return controller;
    }

    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    public void setControllerClassResolver(ControllerClassResolver controllerClassResolver) {
        this.controllerClassResolver = controllerClassResolver;
    }
}
