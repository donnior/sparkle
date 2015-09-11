package me.donnior.sparkle.core;

import me.donnior.sparkle.core.request.SessionStore;
import org.agilej.fava.FList;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.interceptor.Interceptor;

/**
 * configuration result for {@link me.donnior.sparkle.config.Config}
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