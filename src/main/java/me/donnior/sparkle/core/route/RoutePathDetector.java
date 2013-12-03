package me.donnior.sparkle.core.route;

import me.donnior.sparkle.WebRequest;

public class RoutePathDetector {

    //TODO remove this class because the getting path logic was moved to WebRequest
    public static String extractPath(WebRequest webRequest) {
        return webRequest.getPath();
    }
    
}

