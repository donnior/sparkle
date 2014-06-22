package me.donnior.sparkle.core.route;

import me.donnior.sparkle.WebRequest;

/**
 * resovler for getting the most appropriate RouteBuilder for one request
 * 
 */
public interface RouteBuilderResolver {
    /**
     * Find the match closest RouteBuilder for given request.
     * @param request
     * @return
     */
    RouteBuilder match(final WebRequest webRequest);
    
}
