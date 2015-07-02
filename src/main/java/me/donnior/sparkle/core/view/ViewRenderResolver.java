package me.donnior.sparkle.core.view;

import me.donnior.sparkle.core.ActionMethod;
import me.donnior.sparkle.core.view.ViewRender;

/**
 * Get the appropriate ViewRender for action result from the view renders registered in application.
 *
 */
public interface ViewRenderResolver {

    ViewRender resolveViewRender(ActionMethod actionMethod, Object result);
    
}
