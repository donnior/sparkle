package org.agilej.sparkle.core.request;

import com.google.common.base.Strings;
import org.agilej.sparkle.Cookie;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.util.AESKeyGenerator;
import org.agilej.sparkle.util.MessageEncryptor;

import java.security.Key;
import java.util.HashMap;
import java.util.Map;

/**
 * Store session values in cookie, use a app-scope configured secret_key_base to encrypt data like Rails.
 */
public class CookieBasedSessionStore implements SessionStore{

    private String appSecret;

    /**
     * construct instance with determined app_secret. {@link #determineAppSecret()}
     */
    public CookieBasedSessionStore(){
        this.appSecret = determineAppSecret();
//        this.appSecret = "xxxxx";
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
        Map<String, Object> sessionData = getSessionData(request);
        if (obj == null) {
            sessionData.remove(name);
        } else {
            sessionData.put(name, obj);
        }
        rewriteSessionToCookie(request, sessionData);
    }

    @Override
    public Object get(WebRequest request, String name) {
        Map<String, Object> sessionData = getSessionData(request);
        return sessionData.get(name);
    }

    @Override
    public void remove(WebRequest request, String name) {
        this.set(request, name, null);
    }

    private void rewriteSessionToCookie(WebRequest request, Map<String, Object> sessionData){

        if (sessionData == null || sessionData.isEmpty()) {
            return ;
        }
        SparkleException.throwIf(Strings.isNullOrEmpty(this.appSecret),
                "Must specify 'serect_base' for CookieBasedSessionStore");

        Key k = AESKeyGenerator.generateKey(this.appSecret.getBytes());

        MessageEncryptor encryptor = new MessageEncryptor(k);
        String result = (String) encryptor.encryptAndSign(sessionData);
        request.getWebResponse().addCookie(
                new Cookie(cookieNameForSession()).value(result).maxAge(maxAgeForSessionCookie()));
    }

    private Map<String, Object> getSessionData(WebRequest request){
        if (request.getAttribute("session_map") != null) {
            return request.getAttribute("session_map");
        }
        Map<String, Object> map = this.extractSessionFromCookie(request);
        request.setAttribute("session_map", map);
        return map;
    }

    private Map<String, Object> extractSessionFromCookie(WebRequest request){
        Cookie cookie = request.cookie(cookieNameForSession());
        if (cookie == null){
            return new HashMap<>();
        }

        SparkleException.throwIf(
                Strings.isNullOrEmpty(this.appSecret), "Must specify 'serect_base' for CookieBasedSessionStore");
        Key k = AESKeyGenerator.generateKey(this.appSecret.getBytes());

        try {
            MessageEncryptor encryptor = new MessageEncryptor(k);
            Object obj = encryptor.decryptAndVerify(cookie.value());
            //decode session cookie item, construct map
            return (Map) obj;
        } catch (Exception ex){
            //TODO add error handler
            return new HashMap<>();
        }
    }

    private String cookieNameForSession(){
        return "demo2_session";
    }

    private int maxAgeForSessionCookie(){
        return Integer.MAX_VALUE;
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
        //TODO
        return null;
    }

    public void setSecret(String secretBase) {
        this.appSecret = secretBase;
    }

    public String getSecret() {
        return this.appSecret;
    }
}
