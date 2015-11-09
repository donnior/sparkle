package org.agilej.sparkle.route;

import org.agilej.sparkle.annotation.PathVariable;

/**
 * interface to define your route rule. You can use it as a fluency interface like: <br />
 *
 * <pre><code>
 *     router.match("/user/{name}").withPost().to("userController#show");
 * </code></pre>
 *
 */
public interface Router {

    /**
     *
     * Make the router match with a uri path. The path can have path variable use '{}', like "/user/{username}"
     *
     * And the path variable can be accessed as method argument with annotation {@link PathVariable}
     *
     * <br /><br />
     * <pre><code>
     * public String action(@PathVariable String username){
     *     //your code
     * }
     * </code></pre>
     *
     */
    HttpScopedRoutingBuilder match(String path);
    
}
