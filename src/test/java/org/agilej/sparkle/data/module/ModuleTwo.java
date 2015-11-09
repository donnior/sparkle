package org.agilej.sparkle.data.module;

import org.agilej.sparkle.route.AbstractRouteModule;
import org.agilej.sparkle.route.Router;

public class ModuleTwo extends AbstractRouteModule{

    public void configure() {
        get("/user/index").to("user#index");
    }

    @Override
    public void config(Router router) {
        // TODO Auto-generated method stub
        
    }

}
