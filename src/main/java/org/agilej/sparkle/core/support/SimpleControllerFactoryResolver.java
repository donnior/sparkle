package org.agilej.sparkle.core.support;

import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.core.action.ControllerFactoryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *     Resolve {@link ControllerFactory} from configuration, will fallback to default {@link GuiceControllerFactory} if not configured.
 *
 * <p>
 *     When resolve controller factory from config it follows these steps:
 *     <ol> Try to get controller factory instance which user set in config
 *     through {@link org.agilej.sparkle.config.Config#setControllerFactory(ControllerFactory)} </ol>
 *     <ol> Try to get controller factory class which user set in config
 *     through {@link org.agilej.sparkle.config.Config#setControllerFactoryClass(Class)}, and initialize it.
 *     </ol>
 *     <ol> Fallback default {@link GuiceControllerFactory}
 *     </ol>
 *
 * </p>
 *
 * @see org.agilej.sparkle.config.Config
 *
 */
public class SimpleControllerFactoryResolver implements ControllerFactoryResolver{

    private final static Logger logger = LoggerFactory.getLogger(SimpleControllerFactoryResolver.class);

    @Override
    public ControllerFactory get(ConfigResult config) {
        ControllerFactory controllerFactory = config.getControllerFactory();

        if((controllerFactory == null) && (config.getControllerFactoryClass() != null)){
            controllerFactory = (ControllerFactory) ReflectionUtil.initialize(config.getControllerFactoryClass());
        }

        if (controllerFactory == null){
            controllerFactory = defaultControllerFactory();
        }

        logger.info("Resolved ControllerFactory : {} ", controllerFactory.getClass().getSimpleName());
        return controllerFactory;
    }

    /**
     * default controller factory,  which is {@link GuiceControllerFactory}
     * @return default google guice based controller factory
     */
    public ControllerFactory defaultControllerFactory(){
        return new GuiceControllerFactory();
    }

}
