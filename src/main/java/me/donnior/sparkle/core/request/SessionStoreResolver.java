package me.donnior.sparkle.core.request;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.core.ConfigResult;

/**
 * resolver for {@link SessionStore}, will fallback to a default session store if user not config it.
 */
public class SessionStoreResolver {

    /**
     * resolve session store from config, if user not config it must return a default session store.
     * @param config
     * @return
     */
    public SessionStore resolve(ConfigResult config){
        Class<? extends SessionStore> sessionStoreClass = config.getSessionStoreClass();
        if (sessionStoreClass == null) {
            return defaultSessionStore();
        } else {
            return (SessionStore) ReflectionUtil.initialize(sessionStoreClass);
        }
    }

    public SessionStore defaultSessionStore(){
        return new CookieBasedSessionStore();
    }

}
