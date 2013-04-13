package me.donnior.sparkle.data.module;

import me.donnior.sparkle.route.AbstractRouteModule;
import me.donnior.sparkle.route.Router;

public class ModuleTwo extends AbstractRouteModule{

    public void configure() {
        get("/user/index").to("user#index");
    }

    @Override
    public void config(Router router) {
        // TODO Auto-generated method stub
        
    }

}
