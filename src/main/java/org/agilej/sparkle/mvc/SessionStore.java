package org.agilej.sparkle.mvc;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.annotation.UserConfigurable;

/**
 * session data store. For one Sparkle application, it will only hold one SessionStore instance,
 * so the implementation should thread-safe.
 */
@UserConfigurable
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
     * @param request the request will get session data from it
     * @param name session data's name
     * @return session data, null if not exist
     */
    Object get(WebRequest request, String name);


    /**
     * remove session data for given name in request
     * @param request the request will remove session data from it
     * @param name the name of data in session
     */
    void remove(WebRequest request, String name);

}
