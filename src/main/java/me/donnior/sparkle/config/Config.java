package me.donnior.sparkle.config;

import me.donnior.sparkle.Environment;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.core.request.SessionStore;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.interceptor.Interceptor;

public interface Config {

    /**
     * Register customized view renders. 
     */
    void registerViewRenderClass(Class<? extends ViewRender> clz);
    
    
    void registerControllerPackages(String... packages);
    
    
    void registerBasePackage(String basePackage);
    
    void setMode(Environment.Mode mode);
    
    void registerInterceptor(Interceptor interceptor);

    void setSessionStoreClass(Class<? extends SessionStore> sessionStoreClass);

    void setControllerFactory(ControllerFactory controllerFactory);

    void setControllerFactoryClass(Class<? extends ControllerFactory> controllerFactoryClass);
    
}
