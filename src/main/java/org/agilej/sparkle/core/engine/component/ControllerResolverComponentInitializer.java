package org.agilej.sparkle.core.engine.component;

import org.agilej.sparkle.mvc.ControllerFactory;
import org.agilej.sparkle.core.handler.SimpleControllerFactoryResolver;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.engine.ComponentInitializer;
import org.agilej.sparkle.core.ext.EnvSpecific;
import org.agilej.sparkle.core.handler.ControllerClassResolver;
import org.agilej.sparkle.core.handler.ControllerClassScanner;
import org.agilej.sparkle.core.handler.ControllerClassHolder;
import org.agilej.sparkle.core.handler.SimpleControllerResolver;

import java.util.Map;

public class ControllerResolverComponentInitializer implements ComponentInitializer {

    @Override
    public <T> T initializeComponent(ConfigResult config, EnvSpecific specific) {
        //TODO how to deal with multi controller packages
        Map<String, Class<?>> scannedControllers = new ControllerClassScanner().scanControllers(config.getBasePackage());

        ControllerClassResolver controllerClassResolver = new ControllerClassHolder();
        controllerClassResolver.registerControllers(scannedControllers, true);

        ControllerFactory controllerFactory = new SimpleControllerFactoryResolver().get(config);

        SimpleControllerResolver simpleControllerInstanceResolver = new SimpleControllerResolver();
        simpleControllerInstanceResolver.setControllerFactory(controllerFactory);
        simpleControllerInstanceResolver.setControllerClassResolver(controllerClassResolver);
        return (T) simpleControllerInstanceResolver;
    }
}
