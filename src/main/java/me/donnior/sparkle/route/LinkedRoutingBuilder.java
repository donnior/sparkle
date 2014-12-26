package me.donnior.sparkle.route;

public interface LinkedRoutingBuilder {

    /**
     * Define the route rule to a controller-class's action method. The syntax is "controller#action".
     *
     * The controller means a name for one controller class.
     * you must define it in the annotation {@link me.donnior.sparkle.annotation.Controller}. Like:
     *
     * <pre>
     * <code>
     *
     * {@literal @}Controller("user")
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

}
