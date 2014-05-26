package me.donnior.sparkle.ext;

import me.donnior.sparkle.core.resolver.ArgumentResolverManager;
import me.donnior.sparkle.core.view.ViewRenderManager;
import me.donnior.sparkle.engine.RequestLifeCycleManager;

public interface EnvSpecific {

    ViewRenderManager getViewRendersResovler();

    ArgumentResolverManager getArgumentResolverManager();
    
    RequestLifeCycleManager getLifeCycleManager();

}
