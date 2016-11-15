package org.agilej.sparkle.core.action;

import org.agilej.sparkle.core.annotation.Singleton;
import org.agilej.sparkle.core.config.ControllerFactoryConfiguration;

@Singleton
public interface ControllerFactoryResolver {

    ControllerFactory get(ControllerFactoryConfiguration config);

}
