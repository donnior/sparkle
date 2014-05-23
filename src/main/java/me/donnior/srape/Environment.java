package me.donnior.srape;

public interface Environment {

    <T> T get(String key);
    
    boolean contains(String key);
    
}
