package me.donnior.sparkle.core.request;


import me.donnior.sparkle.exception.SparkleException;

/**
 * global session store holder
 */
public class SessionStoreHolder {

    private static SessionStore SESSION_STORE;

    public static void set(SessionStore sessionStore){
        SESSION_STORE = sessionStore;
    }

    public static SessionStore get(){
        if (SESSION_STORE == null) {
            throw new SparkleException("SessionStore did not set.");
        }
        return SESSION_STORE;
    }


}
