package me.donnior.sparkle.core.request;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.Session;
import me.donnior.sparkle.core.ConfigResult;

public class SessionStoreResolver {

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
