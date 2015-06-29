package me.donnior.sparkle.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.route.RouteBuilder;

public class PathVariableDetector {

    public static Map<String, String> extractPathVariables(RouteBuilder rd,
            WebRequest webRequest) {
        List<String> values = rd.extractPathVariableValues(webRequest.getPath());
        List<String> names = rd.getPathVariables();
        Map<String, String> map = new HashMap<String, String>();
        for(int i=0; i<names.size(); i++){
            map.put(names.get(i), values.get(i));
        }
        return map;
        
    }

}
