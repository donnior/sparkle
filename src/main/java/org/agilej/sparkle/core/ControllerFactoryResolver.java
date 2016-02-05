package org.agilej.sparkle.core;

import org.agilej.sparkle.core.ConfigResult;
import org.agilej.sparkle.core.ControllerFactory;

public interface ControllerFactoryResolver {

    ControllerFactory get(ConfigResult config);

}
