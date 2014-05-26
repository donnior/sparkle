package me.donnior.sparkle.engine;

import me.donnior.fava.FList;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.interceptor.Interceptor;

public interface ConfigResult {

    String[] getControllerPackages();

    FList<Class<? extends ViewRender>> getCustomizedViewRenders();
    
    String getBasePackage();

    ControllerFactory getControllerFactory();
    
    Class<? extends ControllerFactory> getControllerFactoryClass();

    FList<Interceptor> getInterceptors();

}
