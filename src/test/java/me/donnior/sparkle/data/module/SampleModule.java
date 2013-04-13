package me.donnior.sparkle.data.module;

import me.donnior.sparkle.route.AbstractRouteModule;

public class SampleModule extends AbstractRouteModule {

    @Override
    protected void configure() {
        post("/user");
    }

}