package me.donnior.sparkle.core.support;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.core.ControllerFactoryResolver;
import me.donnior.sparkle.core.support.GuiceControllerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * resolve {@link ControllerFactory} from configuration, will fallback to default {@link GuiceControllerFactory} if not configured.
 *
 * <p>
 *     when resolve controller factory from config it follows these steps:
 *     <ol> Try to get controller factory instance which user set in config
 *     through {@link me.donnior.sparkle.config.Config#setControllerFactory(ControllerFactory)} </ol>
 *     <ol> Try to get controller factory class which user set in config
 *     through {@link me.donnior.sparkle.config.Config#setControllerFactoryClass(Class)}, and initialize it.
 *     </ol>
 * </p>
 *
 */
public class SimpleControllerFactoryResolver implements ControllerFactoryResolver{

    private final static Logger logger = LoggerFactory.getLogger(SimpleControllerFactory.class);

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
     * @return
     */
    public ControllerFactory defaultControllerFactory(){
        return new GuiceControllerFactory();
    }

}
