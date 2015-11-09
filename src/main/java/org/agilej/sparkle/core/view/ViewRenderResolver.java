package org.agilej.sparkle.core.view;

import org.agilej.sparkle.core.ActionMethod;

/**
 * Get the appropriate ViewRender for action result from the view renders registered in application.
 *
 */
public interface ViewRenderResolver {

    ViewRender resolveViewRender(ActionMethod actionMethod, Object result);
    
}
