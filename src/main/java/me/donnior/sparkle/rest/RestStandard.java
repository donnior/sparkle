package me.donnior.sparkle.rest;

import java.util.HashMap;
import java.util.Map;

import me.donnior.sparkle.HTTPMethod;

public class RestStandard {

    public static String defaultActionMethodNameForHttpMethod(HTTPMethod method){
        return methods().get(method);
    }
    
    public static Map<HTTPMethod, String> methods(){
        final Map<HTTPMethod, String> methods = new HashMap<HTTPMethod, String>();
        
        methods.put(HTTPMethod.GET, "index");
        methods.put(HTTPMethod.POST, "save");
        methods.put(HTTPMethod.PUT, "update");
        methods.put(HTTPMethod.DELETE, "destroy");
            
        methods.put(HTTPMethod.OPTIONS, "options");
        methods.put(HTTPMethod.HEAD, "head");
        methods.put(HTTPMethod.TRACE, "trace");
            
        return methods;
    }
    
}
