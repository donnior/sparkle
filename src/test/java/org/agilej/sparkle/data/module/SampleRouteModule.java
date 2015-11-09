package org.agilej.sparkle.data.module;

import org.agilej.sparkle.route.AbstractRouteModule;
import org.agilej.sparkle.route.HttpScopedRoutingBuilder;
import org.agilej.sparkle.route.LinkedRoutingBuilder;

public class SampleRouteModule extends AbstractRouteModule {

    public HttpScopedRoutingBuilder matchResult;
    public LinkedRoutingBuilder postResult;
    public LinkedRoutingBuilder getResult;
    
    @Override
    protected void configure() {
        postResult = post("/user/new");
        getResult = get("/user/1");
        matchResult = match("/user/donnior");
    }
    
}