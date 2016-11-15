package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.Env;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.dev.RouteNotFoundHandler;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.core.route.RouteBuilderHolder;
import org.agilej.sparkle.core.route.RouteBuilderResolver;
import org.agilej.sparkle.core.route.RouteInfo;
import org.agilej.sparkle.core.route.RouterImpl;
import org.agilej.sparkle.http.HTTPStatusCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoutingPhaseHandler extends AbstractPhaseHandler {

    private RouteBuilderResolver routeBuilderResolver;
    private RouteBuilderHolder router;

    private final static Logger logger = LoggerFactory.getLogger(RoutingPhaseHandler.class);

    @Override
    public void handle(WebRequestExecutionContext context) {
        WebRequest webRequest = context.webRequest();
        RouteInfo rd = this.routeBuilderResolver.match(webRequest);

        if(rd == null){
            logger.info("Could not find route for request : [{} {}] \n", webRequest.getMethod(), webRequest.getPath());
            webRequest.getWebResponse().setStatus(HTTPStatusCode.NOT_FOUND);
            if (Env.isDev()){
                new RouteNotFoundHandler(this.router).handle(webRequest);
            }
            this.postHandle(context);
        } else {
            logger.debug("Found route for request : [{} {}] \n", webRequest.getMethod(), webRequest.getPath());
            context.setRoute(rd);
            forwardToNext(context);
        }
    }

    public void setRouteBuilderResolver(RouteBuilderResolver routeBuilderResolver) {
        this.routeBuilderResolver = routeBuilderResolver;
    }

    public void setRouter(RouteBuilderHolder router) {
        this.router = router;
    }

}
