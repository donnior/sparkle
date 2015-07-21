package me.donnior.sparkle.core.request;

import me.donnior.sparkle.WebRequest;

/**
 * Store session values in cookie, use a app-scope configured key to encrypt values
 */
public class CookieBasedSessionStore implements SessionStore{

    private final String appSecret;

    public CookieBasedSessionStore(){
        this.appSecret = determineAppSecret();
    }

    @Override
    public void set(WebRequest request, String name, Object obj) {

    }

    @Override
    public Object get(WebRequest request, String name) {
        return null;
    }


    /**
     *
     * Follow these orders to determine app-secret
     *
     * 1. use argument when start jvm
     * 2. use System property
     * 3. use secret.yml config
     *
     * @return
     */
    public String determineAppSecret(){
        return null;
    }

}
