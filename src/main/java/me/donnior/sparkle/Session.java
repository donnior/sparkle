package me.donnior.sparkle;

public interface Session {

    Session set(String name, Object obj);

    <T> T get(String name);

    String id();

}
