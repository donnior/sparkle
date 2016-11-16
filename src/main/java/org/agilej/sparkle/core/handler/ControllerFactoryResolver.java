package org.agilej.sparkle.core.handler;

import org.agilej.sparkle.core.annotation.Singleton;
import org.agilej.sparkle.core.config.ControllerFactoryConfiguration;
import org.agilej.sparkle.mvc.ControllerFactory;

@Singleton
public interface ControllerFactoryResolver {

    ControllerFactory get(ControllerFactoryConfiguration config);

}
