package me.donnior.sparkle.core.request;

import me.donnior.sparkle.WebRequest;

/**
 * Store session values in cookie, use a app-scope configured secret and key_base to encrypt data like Rails.
 */
public class CookieBasedSessionStore implements SessionStore{

    private final String appSecret;

    /**
     * construct instance with determined app_secret. {@link #determineAppSecret()}
     */
    public CookieBasedSessionStore(){
        this.appSecret = determineAppSecret();
    }

    /**
     * construct instance with given app_secret
     * @param appSecret the app_secret will used to encrypt cookie data
     */
    public CookieBasedSessionStore(String appSecret){
        this.appSecret = appSecret;
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
