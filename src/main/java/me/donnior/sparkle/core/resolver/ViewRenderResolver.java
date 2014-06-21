package me.donnior.sparkle.core.resolver;

import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.view.ViewRender;

/**
 * Get the appropriate ViewRender for action result from a ViewRender list the application has.
 *
 */
public interface ViewRenderResolver {

    ViewRender resolveViewRender(ActionMethodDefinition adf, Object result);
    
}
