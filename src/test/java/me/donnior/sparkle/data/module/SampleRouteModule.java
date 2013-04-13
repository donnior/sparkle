package me.donnior.sparkle.data.module;

import me.donnior.sparkle.route.AbstractRouteModule;
import me.donnior.sparkle.route.HttpScoppedRoutingBuilder;
import me.donnior.sparkle.route.LinkedRoutingBuilder;

public class SampleRouteModule extends AbstractRouteModule{

    public HttpScoppedRoutingBuilder matchResult;
    public LinkedRoutingBuilder postResult;
    public LinkedRoutingBuilder getResult;
    
    @Override
    protected void configure() {
        postResult = post("/user/new");
        getResult = get("/user/1");
        matchResult = match("/user/donnior");
    }
    
}