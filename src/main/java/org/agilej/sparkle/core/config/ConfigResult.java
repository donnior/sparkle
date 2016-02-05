package org.agilej.sparkle.core.config;

import org.agilej.sparkle.config.Config;
import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.core.request.LocaleResolver;
import org.agilej.sparkle.core.request.SessionStore;
import org.agilej.fava.FList;
import org.agilej.sparkle.core.view.JSONSerializerFactory;
import org.agilej.sparkle.core.view.ViewRender;
import org.agilej.sparkle.interceptor.Interceptor;

/**
 * configuration result for {@link Config}
 */
public interface ConfigResult {

    String[] getControllerPackages();

    FList<Class<? extends ViewRender>> getCustomizedViewRenders();
    
    String getBasePackage();

    ControllerFactory getControllerFactory();
    
    Class<? extends ControllerFactory> getControllerFactoryClass();

    FList<Interceptor> getInterceptors();

    Class<? extends SessionStore> getSessionStoreClass();

    Class<? extends LocaleResolver> getLocaleResolverClass();

    String getSecretBase();

    JSONSerializerFactory jsonViewSerializerFactory();

}
