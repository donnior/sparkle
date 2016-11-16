package org.agilej.sparkle.core.ext;

import org.agilej.sparkle.core.engine.RequestLifeCycleManager;

public interface EnvSpecific {
//
//    ViewRenderManager getViewRendersManager();

//    ArgumentResolverManager getArgumentResolverManager();
    
    RequestLifeCycleManager getLifeCycleManager();

    default VendorViewRenderProvider vendorViewRenderProvider(){
        return null;
    }

    default VendorArgumentResolverProvider vendorArgumentResolverProvider() {
        return null;
    }

}
