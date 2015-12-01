package org.agilej.sparkle.core.request;

import org.agilej.sparkle.Session;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.config.Config;

import java.util.Locale;

public abstract class AbstractWebRequest implements WebRequest {

    /**
     * generate session id, or delegate to vendor's session id like servlet
     * @return
     */
    @Override
    public String getSessionId(){
        throw new RuntimeException("Not implemented yet");
    }

    @Override
    public Session session() {
        SessionStore sessionStore = sessionStore();
        return new SessionFacade(sessionStore, this);
    }

    /**
     * return application scoped {@link SessionStore}, if not configured in {@link Config},
     * must return a default one.
     * @return application scoped sessionStore
     */
    protected SessionStore sessionStore(){
        return SessionStoreHolder.get();
    }

    @Override
    public Locale locale() {
        return localeResolver().resolveLocale(this);
    }

    /**
     * return application scoped {@link LocaleResolver}, if not configured in {@link Config},
     * must return a default one.
     * @return application scoped localeResolver
     */
    protected LocaleResolver localeResolver() {
        return LocaleResolverHolder.get();
    }

}