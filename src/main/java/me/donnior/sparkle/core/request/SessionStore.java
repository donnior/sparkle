package me.donnior.sparkle.core.request;

import me.donnior.sparkle.WebRequest;

/**
 * session data store
 */
public interface SessionStore {

    /**
     * set the given name-value pair data to request
     * @param request request which will store the session data
     * @param name data's name in session
     * @param value the data will stored to session
     */
    void set(WebRequest request, String name, Object value);

    /**
     * get session data with given name from request
     * @param request the request will
     * @param name session data's name
     * @return session data, null if not exist
     */
    Object get(WebRequest request, String name);

}
