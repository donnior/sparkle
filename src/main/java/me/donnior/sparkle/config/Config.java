package me.donnior.sparkle.config;

import me.donnior.sparkle.Environment;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.interceptor.Interceptor;

public interface Config {

    /**
     * Register customized view renders. 
     */
    void registerViewRenderClass(Class<? extends ViewRender> clz);
    
    
    void registerControllerPackages(String... packages);
    
    
    void registerBasePackage(String basePackage);
    
    void setMode(Environment.Mode mode);
    
    void registerInterceptor(Interceptor interceptor);
    
}
