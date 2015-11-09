package org.agilej.sparkle;

public interface Params {

    String get(String name);

    String get(String name, String defaultValue);
    
    String[] gets(String name);
    
    <T> T get(String name, Class<T> clz);

    Integer getInt(String name);

    Integer getInt(String name, Integer defaultValue);

    Float getFloat(String name);

    Float getFloat(String name, Float defaultValue);


    
}
