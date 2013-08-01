package me.donnior.sparkle.config;

import me.donnior.fava.FList;
import me.donnior.sparkle.view.ViewRender;

public interface ConfigAware {

    String[] getControllerPackages();

    FList<Class<? extends ViewRender>> getViewRenders();
    
    String getBasePackage();

}
