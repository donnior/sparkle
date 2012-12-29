package me.donnior.sparkle.route;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

public abstract class AbstractRouteModule implements RouteModule {

    Router router;

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

    protected Router router() {
        checkState(router != null, "The router can only be used inside configure()");
        return router;
    }

    protected HttpScoppedRoutingBuilder route(String path) {
        return router().route(path);
    }
    
    protected LinkedRoutingBuilder get(String path) {
        return router().route(path).withGet();
    }
    
    protected LinkedRoutingBuilder post(String path) {
        return router().route(path).withPost();
    }

}
