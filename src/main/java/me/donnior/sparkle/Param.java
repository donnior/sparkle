package me.donnior.sparkle;

public interface Param {

    String get(String name);
    
    String[] gets(String name);
    
    <T> T get(String name, Class<T> clz);
    
}
