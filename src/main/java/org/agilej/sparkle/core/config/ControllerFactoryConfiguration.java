package org.agilej.sparkle.core.config;

import org.agilej.sparkle.mvc.ControllerFactory;

public interface ControllerFactoryConfiguration {

    ControllerFactory getControllerFactory();

    Class<? extends ControllerFactory> getControllerFactoryClass();

}
