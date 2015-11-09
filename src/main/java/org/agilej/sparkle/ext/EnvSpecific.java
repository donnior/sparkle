package org.agilej.sparkle.ext;

import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.agilej.sparkle.engine.RequestLifeCycleManager;

public interface EnvSpecific {
//
//    ViewRenderManager getViewRendersManager();

    ArgumentResolverManager getArgumentResolverManager();
    
    RequestLifeCycleManager getLifeCycleManager();

    default VendorViewRenderProvider vendorViewRenderProvider(){
        return null;
    }

}
