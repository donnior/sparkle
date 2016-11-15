package org.agilej.sparkle.core.engine.component;

import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.core.action.SimpleControllerFactoryResolver;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.engine.ComponentInitializer;
import org.agilej.sparkle.core.ext.EnvSpecific;
import org.agilej.sparkle.core.method.ControllerClassResolver;
import org.agilej.sparkle.core.method.ControllerClassScanner;
import org.agilej.sparkle.core.method.ControllersHolder;
import org.agilej.sparkle.core.method.SimpleControllerInstanceResolver;

import java.util.Map;

public class ControllerResolverComponentInitializer implements ComponentInitializer {

    @Override
    public <T> T initializeComponent(ConfigResult config, EnvSpecific specific) {
        //TODO how to deal with multi controller packages
        Map<String, Class<?>> scannedControllers = new ControllerClassScanner().scanControllers(config.getBasePackage());

        ControllerClassResolver controllerClassResolver = new ControllersHolder();
        controllerClassResolver.registerControllers(scannedControllers, true);

        ControllerFactory controllerFactory = new SimpleControllerFactoryResolver().get(config);

        SimpleControllerInstanceResolver simpleControllerInstanceResolver = new SimpleControllerInstanceResolver();
        simpleControllerInstanceResolver.setControllerFactory(controllerFactory);
        simpleControllerInstanceResolver.setControllerClassResolver(controllerClassResolver);
        return (T) simpleControllerInstanceResolver;
    }
}
