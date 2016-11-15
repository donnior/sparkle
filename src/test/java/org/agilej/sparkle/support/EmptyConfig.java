package org.agilej.sparkle.support;

import org.agilej.sparkle.core.argument.ArgumentResolver;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.core.config.ControllerFactoryConfiguration;
import org.agilej.sparkle.core.request.LocaleResolver;
import org.agilej.sparkle.core.request.SessionStore;
import org.agilej.sparkle.core.view.JSONSerializerFactory;
import org.agilej.sparkle.core.view.ViewRender;
import org.agilej.sparkle.interceptor.Interceptor;
import org.agilej.fava.FList;

import java.util.List;

public class EmptyConfig implements ConfigResult, ControllerFactoryConfiguration {

    @Override
    public String[] getControllerPackages() {
        return new String[0];
    }

    @Override
    public FList<Class<? extends ViewRender>> getCustomizedViewRenders() {
        return null;
    }

    @Override
    public String getBasePackage() {
        return null;
    }

    @Override
    public ControllerFactory getControllerFactory() {
        return null;
    }

    @Override
    public Class<? extends ControllerFactory> getControllerFactoryClass() {
        return null;
    }

    @Override
    public FList<Interceptor> getInterceptors() {
        return null;
    }

    @Override
    public Class<? extends SessionStore> getSessionStoreClass() {
        return null;
    }

    @Override
    public String getSecretBase() {
        return null;
    }

    @Override
    public Class<? extends LocaleResolver> getLocaleResolverClass() {
        return null;
    }

    @Override
    public JSONSerializerFactory jsonSerializerFactory() {
        return null;
    }

    @Override
    public List<ArgumentResolver> getCustomizedArgumentResolvers() {
        return null;
    }
}

