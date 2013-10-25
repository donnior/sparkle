package me.donnior.sparkle.core.route;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.WebRequest;

public class RoutePathDetector {

    public static String extractPath(WebRequest webRequest) {
        HttpServletRequest request = webRequest.getServletRequest();
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return request.getServletPath();   //wild servlet mapping like "/" or "*.do"
        } 
        return pathInfo;        //otherwise normal mapping like "/cms/*"
    }
    
}

