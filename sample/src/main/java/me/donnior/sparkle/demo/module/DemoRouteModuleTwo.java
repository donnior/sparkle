package me.donnior.sparkle.demo.module;

import me.donnior.sparkle.route.AbstractRouteModule;

public class DemoRouteModuleTwo extends  AbstractRouteModule {

    @Override
    public void configure() {
        get("/user/index").to("user#index");
        post("/users").to("user#save");
    }

}
