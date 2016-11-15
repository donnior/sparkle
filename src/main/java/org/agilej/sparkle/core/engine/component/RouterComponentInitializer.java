package org.agilej.sparkle.core.engine.component;

import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.engine.ComponentInitializer;
import org.agilej.sparkle.core.ext.EnvSpecific;
import org.agilej.sparkle.core.route.RouteBuilder;
import org.agilej.sparkle.core.route.RouteModuleScanner;
import org.agilej.sparkle.core.route.RouterImpl;
import org.agilej.sparkle.core.route.SimpleRouteBuilderResolver;
import org.agilej.sparkle.route.RouteModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class RouterComponentInitializer implements ComponentInitializer {

    private final static Logger logger = LoggerFactory.getLogger(RouterComponentInitializer.class);

    @Override
    public <T> T initializeComponent(ConfigResult config, EnvSpecific specific) {
        RouterImpl router = new RouterImpl();
        List<RouteModule> routeModules = new RouteModuleScanner().scanRouteModule(config.getBasePackage());
        router.install(routeModules);
        for(RouteBuilder rb : router.getRegisteredRouteBuilders()){
            logger.info("Registered route : {}", rb.toString());
        }

        return (T) new SimpleRouteBuilderResolver(router);
    }
}
