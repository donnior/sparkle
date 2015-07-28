package me.donnior.sparkle.core.request;


import me.donnior.sparkle.WebRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * use a in memory hash map to store session values
 */
public class SimpleMemorySessionStore implements SessionStore{


    private final Map<String, Map<String, Object>> sessionMaps;

    public SimpleMemorySessionStore(){
        this.sessionMaps = new HashMap<>();
    }

    @Override
    public void set(WebRequest request, String name, Object obj) {

        sessionMaps.computeIfAbsent(request.getSessionId(), e -> new HashMap<String, Object>()).put(name, obj);
    }

    @Override
    public Object get(WebRequest request, String name) {
        return sessionMaps.computeIfAbsent(request.getSessionId(), e -> new HashMap()).get(name);
    }

    @Override
    public void remove(WebRequest request, String name) {
        sessionMaps.computeIfAbsent(request.getSessionId(), e -> new HashMap()).remove(name);
    }
}
