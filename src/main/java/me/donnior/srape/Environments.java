package me.donnior.srape;

import java.util.HashMap;
import java.util.Map;

public class Environments {
    
    public static Environment envFromMap(Map<String, Object> hashMap) {
        Environment env = new MapBasedEnviroment(hashMap);
        return env;
    }

    public static Environment envWith(String key, Object value) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key, value);
        return envFromMap(map);
    }
    
    public static Environment envWith(String key1, Object value1, String key2, Object value2) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(key1, value1);
        map.put(key2, value2);
        return envFromMap(map);
    }
}
