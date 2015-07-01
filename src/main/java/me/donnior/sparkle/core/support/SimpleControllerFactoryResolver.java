package me.donnior.sparkle.core.support;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.core.ControllerFactoryResolver;
import me.donnior.sparkle.core.support.GuiceControllerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleControllerFactoryResolver implements ControllerFactoryResolver{

    private final static Logger logger = LoggerFactory.getLogger(SimpleControllerFactory.class);

    @Override
    public ControllerFactory get(ConfigResult config) {
        ControllerFactory controllerFactory = config.getControllerFactory();

        if((controllerFactory == null) && (config.getControllerFactoryClass() != null)){
            controllerFactory = (ControllerFactory) ReflectionUtil.initialize(config.getControllerFactoryClass());
        }

        if (controllerFactory == null){
            controllerFactory = new GuiceControllerFactory();
        }

        logger.info("Resolved ControllerFactory : {} ", controllerFactory.getClass().getSimpleName());
        return controllerFactory;
    }
}
