package org.agilej.sparkle;

/**
 * interface for presenting a server side session
 */
public interface Session {

    /**
     *
     * set value to session with given name
     *
     * @param name name of the value
     * @param value the value will set to session
     * @return session itself
     */
    Session set(String name, Object value);

    /**
     * get data from session with given name
     * @param name value's name in session
     * @return value in session, null if not exist
     */
    <T> T get(String name);

    /**
     * session id
     *
     * @return id of this session
     */
    String id();

    /**
     * get data from session with given name, if the value is null
     * will return the default value passed to it
     * @param name
     * @param defaultValue
     * @param <T>
     * @return
     */
    default <T> T get(String name, T defaultValue) {
        T t = get(name);
        return t != null ? t : defaultValue;
    }
}
