package me.donnior.sparkle.core.config;

import me.donnior.fava.FList;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.servlet.ControllerFactory;

public interface ConfigAware {

    String[] getControllerPackages();

    FList<Class<? extends ViewRender>> getViewRenders();
    
    String getBasePackage();

    ControllerFactory getControllerFactory();
    
    Class<? extends ControllerFactory> getControllerFactoryClass();

}
