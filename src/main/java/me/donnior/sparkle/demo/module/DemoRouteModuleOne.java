package me.donnior.sparkle.demo.module;

import me.donnior.sparkle.route.RouteModule;
import me.donnior.sparkle.route.Router;

public class DemoRouteModuleOne implements RouteModule {
    

    @Override
    public void config(Router router) {
        router.route("/session/create").to("projects#index");
    }

}
