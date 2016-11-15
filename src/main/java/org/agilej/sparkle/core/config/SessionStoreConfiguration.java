package org.agilej.sparkle.core.config;

import org.agilej.sparkle.core.request.SessionStore;

public interface SessionStoreConfiguration {

    Class<? extends SessionStore> getSessionStoreClass();

    /**
     * secret key, used for encrypt session in cookie
     * @return
     */
    String getSecretBase();

}
