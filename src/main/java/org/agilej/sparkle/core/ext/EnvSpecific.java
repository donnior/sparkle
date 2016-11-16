package org.agilej.sparkle.core.ext;

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
