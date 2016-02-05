package org.agilej.sparkle.core.engine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.route.RouteBuilder;

@Deprecated
public class PathVariableDetector {

    @Deprecated
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
