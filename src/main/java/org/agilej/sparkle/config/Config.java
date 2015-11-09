package org.agilej.sparkle.config;

import org.agilej.sparkle.Env;
import org.agilej.sparkle.core.ControllerFactory;
import org.agilej.sparkle.core.request.SessionStore;
import org.agilej.sparkle.core.view.ViewRender;
import org.agilej.sparkle.interceptor.Interceptor;
import org.agilej.sparkle.core.support.GuiceControllerFactory;

/**
 * interface for customize application's setting.
 */
public interface Config {

    /**
     * Register customized view renders. 
     */
    Config registerViewRenderClass(Class<? extends ViewRender> clz);

    /**
     * Register multi customized view renders.
     *
     * @see #registerViewRenderClass(Class)
     */
    default Config registerViewRenderClasses(Class<? extends ViewRender>[] classes){
        for (Class<? extends  ViewRender> clz : classes){
            this.registerViewRenderClass(clz);
        }
        return this;
    }

    /**
     *
     * @param packages
     */
    Config registerControllerPackages(String... packages);

    /**
     *
     * @param basePackage
     */
    Config registerBasePackage(String basePackage);

    /**
     *
     * @param mode
     */
    Config setMode(Env.Mode mode);

    /**
     *
     * @param interceptor
     */
    Config registerInterceptor(Interceptor interceptor);

    /**
     * set customized session store strategy
     * @param sessionStoreClass the customized session store class.
     */
    Config setSessionStoreClass(Class<? extends SessionStore> sessionStoreClass);

    /**
     * set customized controller factory,
     * the default one will be {@link GuiceControllerFactory}
     *
     * Note if this one is set, {@link #setControllerFactoryClass(Class)} will be ignored.
     *
     * @param controllerFactory
     */
    Config setControllerFactory(ControllerFactory controllerFactory);

    /**
     * set customized controller factory class.
     * This configuration will be used if customized controller-factory is not set,
     *
     * see {@link #setControllerFactory(ControllerFactory)}.
     *
     * @param controllerFactoryClass
     */
    Config setControllerFactoryClass(Class<? extends ControllerFactory> controllerFactoryClass);

    /**
     * set secret base for encryption.
     * @param secretBase
     */
    Config setSecretBase(String secretBase);
    
}
