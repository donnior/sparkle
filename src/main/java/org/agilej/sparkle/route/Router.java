package org.agilej.sparkle.route;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.annotation.PathVariable;

import java.util.function.Function;

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
     * And the path variable can be accessed as method handler with annotation {@link PathVariable}
     *
     * <br /><br />
     * <pre><code>
     * public String handler(@PathVariable String username){
     *     //your code
     * }
     * </code></pre>
     *
     */
    HttpScopedRoutingBuilder match(String path);

    default LinkedRoutingBuilder get(String path) {
        return this.match(path).withGet();
    }

    default LinkedRoutingBuilder post(String path) {
        return this.match(path).withPost();
    }

    default LinkedRoutingBuilder delete(String path) {
        return this.match(path).withDelete();
    }

    default LinkedRoutingBuilder put(String path) {
        return this.match(path).withPut();
    }

    default void get(String path, Function<WebRequest, Object> function){
        this.get(path).to(function);
    }

    default void post(String path, Function<WebRequest, Object> function){
        this.post(path).to(function);
    }

    default void delete(String path, Function<WebRequest, Object> function){
        this.delete(path).to(function);
    }

    default void put(String path, Function<WebRequest, Object> function){
        this.put(path).to(function);
    }
}
