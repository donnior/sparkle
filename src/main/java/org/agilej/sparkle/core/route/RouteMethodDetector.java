package org.agilej.sparkle.core.route;

import org.agilej.sparkle.HTTPMethod;
import org.agilej.sparkle.WebRequest;

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

