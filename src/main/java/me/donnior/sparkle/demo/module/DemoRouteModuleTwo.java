package me.donnior.sparkle.demo.module;

import me.donnior.sparkle.route.AbstractRouteModule;
import me.donnior.sparkle.route.Router;

public class DemoRouteModuleTwo extends  AbstractRouteModule {

    @Override
    public void config(Router router) {
        router.route("/session/destroy").to("session#destroy");
    }

}
