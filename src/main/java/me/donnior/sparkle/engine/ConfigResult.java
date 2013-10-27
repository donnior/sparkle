package me.donnior.sparkle.engine;

import me.donnior.fava.FList;
import me.donnior.sparkle.core.view.ViewRender;

public interface ConfigResult {

    String[] getControllerPackages();

    FList<Class<? extends ViewRender>> getCustomizedViewRenders();
    
    String getBasePackage();

    ControllerFactory getControllerFactory();
    
    Class<? extends ControllerFactory> getControllerFactoryClass();

}
