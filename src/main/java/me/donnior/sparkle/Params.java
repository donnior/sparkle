package me.donnior.sparkle;

public interface Params {

    String get(String name);
    
    String[] gets(String name);
    
    <T> T get(String name, Class<T> clz);
    
}
