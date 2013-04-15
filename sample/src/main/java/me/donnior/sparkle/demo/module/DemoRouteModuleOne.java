package me.donnior.sparkle.demo.module;

import me.donnior.sparkle.route.RouteModule;
import me.donnior.sparkle.route.Router;

public class DemoRouteModuleOne implements RouteModule {
    

    @Override
    public void config(Router router) {
        
        router.match("/").to("home#index");
        
        router.match("/projects").to("projects#index");
        
        router.match("/projects").matchParams("a=1","b!=2", "c").to("projects#index2");
        
        router.match("/projects/{name}").withPost().to("projects#save");
        
        router.match("/projects/{name}").to("projects#index");
        
        router.match("/accounts/{name}").to("projects#index");
        
        router.match("/users/{name}/topics/{id}").to("projects");
        
        router.match("/project/jsons").to("projects#jsons");

    }

}
