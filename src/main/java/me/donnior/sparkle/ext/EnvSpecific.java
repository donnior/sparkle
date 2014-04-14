package me.donnior.sparkle.ext;

import me.donnior.sparkle.core.resolver.ParamResolversManager;
import me.donnior.sparkle.core.view.ViewRendersResovler;
import me.donnior.sparkle.engine.RequestLifeCycleManager;

public interface EnvSpecific {

    ViewRendersResovler getViewRendersResovler();

    ParamResolversManager getParamsResolverManager();
    
    RequestLifeCycleManager getLifeCycleManager();

}
