package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.HTTPMethod;

public class RouteMethodDetector {

    public static HTTPMethod detectMethod(HttpServletRequest request) {
        if ("get".equals(request.getMethod().toLowerCase())) {
            return HTTPMethod.GET;
        }
        if ("post".equals(request.getMethod().toLowerCase())) {
            return HTTPMethod.POST;
        }
        return HTTPMethod.GET;
    }

    
}

