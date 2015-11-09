package org.agilej.sparkle.core.request;


import org.agilej.sparkle.exception.SparkleException;

/**
 * global session store holder
 */
public class SessionStoreHolder {

    private static SessionStore SESSION_STORE;

    /**
     * set global SessionStore instance
     * @param sessionStore to set
     */
    public static void set(SessionStore sessionStore){
        SESSION_STORE = sessionStore;
    }

    /**
     *
     * get the global SessionStore instance
     *
     * @return the global SessionStore instance
     * @throws SparkleException if the global SessionStore instance is not set
     */
    public static SessionStore get(){
        if (SESSION_STORE == null) {
            throw new SparkleException("SessionStore did not set.");
        }
        return SESSION_STORE;
    }

    /**
     * reset the global SessionStore instance to null
     */
    public static void reset(){
        SESSION_STORE = null;
    }

}
