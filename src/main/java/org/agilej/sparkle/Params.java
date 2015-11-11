package org.agilej.sparkle;

/**
 * interface for presenting {@link WebRequest}'s params container.
 */
public interface Params {

    /**
     *
     * @param name
     * @return
     */
    String get(String name);

    /**
     * get parameter, or return default value.
     * @param name
     * @param defaultValue
     * @return
     */
    String get(String name, String defaultValue);

    /**
     * get all value for parameter
     * @param name
     * @return
     */
    String[] gets(String name);
    
    <T> T get(String name, Class<T> clz);

    /**
     * get parameter value as int
     * @param name
     * @return
     */
    Integer getInt(String name);

    /**
     * get parameter value as int, or return a default value
     *
     * @param name
     * @param defaultValue
     * @return
     */
    Integer getInt(String name, Integer defaultValue);

    /**
     * get parameter value as float
     * @param name
     * @return
     */
    Float getFloat(String name);

    /**
     * get parameter value as float, or return a default value.
     * @param name
     * @param defaultValue
     * @return
     */
    Float getFloat(String name, Float defaultValue);


    
}
