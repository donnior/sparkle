package me.donnior.sparkle.core.route;

import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.WebRequest;

public class RouteMethodDetector {

    public static HTTPMethod detectMethod(WebRequest request) {
        if ("get".equals(request.getMethod().toLowerCase())) {
            return HTTPMethod.GET;
        }
        if ("post".equals(request.getMethod().toLowerCase())) {
            return HTTPMethod.POST;
        }
        return HTTPMethod.GET;
    }

    
}

