package me.donnior.sparkle.core.route;

import javax.servlet.http.HttpServletRequest;

public interface RouteBuilderResolver {
    /**
     * Find the match closest RouteBuilder for given request.
     * @param request
     * @return
     */
    RouteBuilder match(final HttpServletRequest request);
    
}
