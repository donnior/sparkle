package me.donnior.sparkle.support;

import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.core.request.SessionStore;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.interceptor.Interceptor;
import org.agilej.fava.FList;

public class EmptyConfig implements ConfigResult{

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
}

