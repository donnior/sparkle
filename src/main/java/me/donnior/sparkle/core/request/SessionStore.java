package me.donnior.sparkle.core.request;

import me.donnior.sparkle.WebRequest;

public interface SessionStore {

    void set(WebRequest request, String name, Object obj);

    Object get(WebRequest request, String name);

}
