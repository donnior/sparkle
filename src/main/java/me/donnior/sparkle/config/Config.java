package me.donnior.sparkle.config;

import me.donnior.sparkle.Environment;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.core.request.SessionStore;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.interceptor.Interceptor;

/**
 * interface for customize application's setting.
 */
public interface Config {

    /**
     * Register customized view renders. 
     */
    void registerViewRenderClass(Class<? extends ViewRender> clz);

    /**
     *
     * @param packages
     */
    void registerControllerPackages(String... packages);

    /**
     *
     * @param basePackage
     */
    void registerBasePackage(String basePackage);

    /**
     *
     * @param mode
     */
    void setMode(Environment.Mode mode);

    /**
     *
     * @param interceptor
     */
    void registerInterceptor(Interceptor interceptor);

    /**
     * set customized session store strategy
     * @param sessionStoreClass the customized session store class.
     */
    void setSessionStoreClass(Class<? extends SessionStore> sessionStoreClass);

    /**
     * set customized controller factory, the default one will be {@link me.donnior.sparkle.core.support.GuiceControllerFactory}
     * Note if this one is set, {@link #setControllerFactoryClass(Class)} will be ignored.
     * @param controllerFactory
     */
    void setControllerFactory(ControllerFactory controllerFactory);

    /**
     * set customized controller factory class. This configuration will be used if customized controller-factory is not set,
     * see {@link #setControllerFactory(ControllerFactory)}.
     *
     * @param controllerFactoryClass
     */
    void setControllerFactoryClass(Class<? extends ControllerFactory> controllerFactoryClass);

    /**
     * set secret base for encryption.
     * @param secrectBase
     */
    void setSecrectBase(String secrectBase);
    
}
