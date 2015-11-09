package org.agilej.sparkle.core;

import org.agilej.sparkle.config.Config;
import org.agilej.sparkle.core.request.SessionStore;
import org.agilej.fava.FList;
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

    String getSecretBase();

}
