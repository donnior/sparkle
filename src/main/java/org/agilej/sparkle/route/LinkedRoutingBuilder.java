package org.agilej.sparkle.route;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.annotation.Controller;

import java.util.function.Function;

public interface LinkedRoutingBuilder {

    /**
     * Define the route rule to a controller-class's handler method. The syntax is "controller#handler".
     *
     * The controller means a name for one controller class.
     * you must define it in the annotation {@link Controller}. Like:
     *
     * <pre>
     * <code>
     *
     * {@literal@}Controller("user")
     * public class UserController{
     *
     * }
     * </code>
     * </pre>
     *
     *
     *
     */
    void to(String controllerAndAction);

    /**
     * Define the route rule to a request process function witch only returns a json model.
     *
     *
     * <pre>
     * <code>
     *
     * router.match("/api").to(request -> {
     *     return e-> e.expose(200).withName("status");
     * });
     *
     * </code>
     * </pre>
     *
     * @param function
     */
    void to(Function<WebRequest, Object> function);

}
