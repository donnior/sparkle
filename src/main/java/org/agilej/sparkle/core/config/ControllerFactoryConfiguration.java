package org.agilej.sparkle.core.config;

import org.agilej.sparkle.core.action.ControllerFactory;

public interface ControllerFactoryConfiguration {

    ControllerFactory getControllerFactory();

    Class<? extends ControllerFactory> getControllerFactoryClass();

}
