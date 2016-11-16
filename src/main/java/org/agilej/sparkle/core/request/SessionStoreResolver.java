package org.agilej.sparkle.core.request;

import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.core.config.SessionStoreConfiguration;
import org.agilej.sparkle.mvc.SessionStore;

/**
 * resolver for {@link SessionStore}, will fallback to a default session store if user not config it.
 */
public class SessionStoreResolver {

    private SessionStoreConfiguration config;

    public SessionStoreResolver(SessionStoreConfiguration config){
        this.config = config;
    }

    /**
     * resolve session store from config, if user not config it must return a default session store.
     * @return
     */
    public SessionStore resolve(){
        Class<? extends SessionStore> sessionStoreClass = config.getSessionStoreClass();
        if (sessionStoreClass == null || CookieBasedSessionStore.class.equals(sessionStoreClass)) {
            return defaultSessionStore();
        } else {
            return (SessionStore) ReflectionUtil.initialize(sessionStoreClass);
        }
    }

    /**
     * return a default session store instance, which is {@link CookieBasedSessionStore}
     * @return
     */
    public SessionStore defaultSessionStore(){
        //TODO deal with secret_base is null.
        //SparkleException.throwIf(Strings.isNullOrEmpty(config.getSecretBase()),
        //        "To use CookieBasedSessionStore you must specify the 'secretBase' in application configuration");

        return new CookieBasedSessionStore(config.getSecretBase());
    }

}
