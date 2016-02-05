package org.agilej.sparkle.core.action;

import org.agilej.sparkle.core.config.ConfigResult;

public interface ControllerFactoryResolver {

    ControllerFactory get(ConfigResult config);

}
