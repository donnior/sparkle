package me.donnior.sparkle.demo.module;

import me.donnior.sparkle.route.RouteModule;
import me.donnior.sparkle.route.Router;

public class DemoRouteModuleOne implements RouteModule {
    

    @Override
    public void config(Router router) {
        router.route("/").to("home#index");
        router.route("/projects").to("projects#index");
        router.route("/accounts/{name}").to("projects#index");
        router.route("/users/{name}/topics/{id}").to("projects#index");
    }

}
