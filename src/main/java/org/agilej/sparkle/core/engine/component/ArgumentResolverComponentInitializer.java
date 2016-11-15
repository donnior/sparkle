package org.agilej.sparkle.core.engine.component;

import org.agilej.sparkle.core.argument.ArgumentResolverRegistration;
import org.agilej.sparkle.core.argument.SimpleArgumentResolverManager;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.engine.ComponentInitializer;
import org.agilej.sparkle.core.ext.EnvSpecific;
import org.agilej.sparkle.core.ext.VendorArgumentResolverProvider;

public class ArgumentResolverComponentInitializer implements ComponentInitializer {
    @Override
    public <T> T initializeComponent(ConfigResult config, EnvSpecific specific) {
        ArgumentResolverRegistration registration = new ArgumentResolverRegistration();
        registration.registerAppScopedArgumentResolver(config.getCustomizedArgumentResolvers());

        VendorArgumentResolverProvider vendorArgumentResolverProvider = specific.vendorArgumentResolverProvider();
        if (vendorArgumentResolverProvider != null) {
            registration.registerVendorArgumentResolvers(vendorArgumentResolverProvider.vendorArgumentResolvers());
        }

        SimpleArgumentResolverManager argumentResolverManager = new SimpleArgumentResolverManager();
        argumentResolverManager.registerArgumentResolvers(registration.getAllOrderedArgumentResolvers());

        return (T) argumentResolverManager;
    }
}
