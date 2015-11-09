package org.agilej.sparkle.data.module;

import org.agilej.sparkle.route.AbstractRouteModule;

public class SampleModule extends AbstractRouteModule {

    @Override
    protected void configure() {
        post("/user");
    }

}