package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

public class RoutePathDetector {

    public static String extractPath(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return request.getServletPath();   //wild servlet mapping like "/" or "*.do"
        } 
        return pathInfo;        //otherwise normal mapping like "/cms/*"
    }
    
}

