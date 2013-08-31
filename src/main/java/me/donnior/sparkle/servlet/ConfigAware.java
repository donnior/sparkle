package me.donnior.sparkle.servlet;

import me.donnior.fava.FList;
import me.donnior.sparkle.core.view.ViewRender;

public interface ConfigAware {

    String[] getControllerPackages();

    FList<Class<? extends ViewRender>> getViewRenders();
    
    String getBasePackage();

    ControllerFactory getControllerFactory();
    
    Class<? extends ControllerFactory> getControllerFactoryClass();

}
