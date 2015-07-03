package me.donnior.sparkle.core.route;

import me.donnior.sparkle.WebRequest;

/**
 * resolver for getting the most appropriate {@link RouteBuilder} for one request
 * 
 */
public interface RouteBuilderResolver {
    /**
     * Find the match closest RouteBuilder for given request.
     * @param request
     * @return
     */
    RouteBuilder match(final WebRequest request);
    
}
