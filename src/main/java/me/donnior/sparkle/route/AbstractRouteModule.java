package me.donnior.sparkle.route;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 *
 * A support class for {@link RouteModule}s which reduces repetition and results in
 * a more readable configuration. Simply extend this class and implement {@link
 * #configure()}, and call the inherited methods which mirror those found in
 * {@link Router}. For example:
 *
 *
 * <pre>
 *     {@code
 *
 *     class MyRouteModule extends AbstractRouteModule{
 *         public void configure(){
 *             get("/item/{id}").to("items#show");
 *             post("/item/{id}").to("items#update");
 *         }
 *     }
 *
 *     }
 * </pre>
 *
 */
public abstract class AbstractRouteModule implements RouteModule {

    public Router router;

    @Override
    public void config(Router router) {
        checkState(this.router == null, "Re-entry is not allowed.");

        this.router = checkNotNull(router, "builder");
        try {
            configure();
        } finally {
            this.router = null;
        }
    }

    protected abstract void configure();

    public Router router() {
        checkState(router != null, "The router can only be used inside configure()");
        return router;
    }

    protected HttpScopedRoutingBuilder match(String path) {
        return router().match(path);
    }
    
    protected LinkedRoutingBuilder get(String path) {
        return router().match(path).withGet();
    }
    
    protected LinkedRoutingBuilder post(String path) {
        return router().match(path).withPost();
    }

    protected LinkedRoutingBuilder put(String path) {
        return router().match(path).withPut();
    }

    protected LinkedRoutingBuilder delete(String path) {
        return router().match(path).withDelete();
    }

}
