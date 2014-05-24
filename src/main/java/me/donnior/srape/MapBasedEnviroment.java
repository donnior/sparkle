package me.donnior.srape;

import java.util.HashMap;
import java.util.Map;

public class MapBasedEnviroment implements Environment{

    private Map<String, Object> map;

    public MapBasedEnviroment(Map<String, Object> map){
        this.map = new HashMap<String, Object>(map);
    }
    
    @Override
    public boolean contains(String key) {
        return this.map.containsKey(key);
    }

    @Override
    public Object get(String key) {
        return this.map.get(key);
    }

}
