package org.agilej.sparkle.core.engine.component;

import org.agilej.sparkle.core.handler.ActionMethodResolver;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.engine.ComponentInitializer;
import org.agilej.sparkle.core.ext.EnvSpecific;

public class ActionMethodResolverInitializer implements ComponentInitializer {
    @Override
    public <T> T initializeComponent(ConfigResult config, EnvSpecific specific) {
        return (T) new ActionMethodResolver();
    }
}
