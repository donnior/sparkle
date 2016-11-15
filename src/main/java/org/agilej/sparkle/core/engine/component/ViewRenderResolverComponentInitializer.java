package org.agilej.sparkle.core.engine.component;

import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.engine.ComponentInitializer;
import org.agilej.sparkle.core.ext.EnvSpecific;
import org.agilej.sparkle.core.ext.VendorViewRenderProvider;
import org.agilej.sparkle.core.view.SimpleViewRenderResolver;
import org.agilej.sparkle.core.view.ViewRenderRegistration;

public class ViewRenderResolverComponentInitializer implements ComponentInitializer {
    @Override
    public <T> T initializeComponent(ConfigResult config, EnvSpecific specific) {
        ViewRenderRegistration viewRenderRegistration = new ViewRenderRegistration();
        viewRenderRegistration.registerAppScopedViewRender(config.getCustomizedViewRenders());

        VendorViewRenderProvider vendorViewRenderProvider = specific.vendorViewRenderProvider();
        if (vendorViewRenderProvider != null){
            viewRenderRegistration.registerVendorViewRenders(vendorViewRenderProvider.vendorViewRenders());
        }
        //TODO remove ViewRenderManager, inject Config to SimpleViewRenderResolver directly and resolve all renders
        return (T) new SimpleViewRenderResolver(viewRenderRegistration.getAllOrderedViewRenders());
    }
}
