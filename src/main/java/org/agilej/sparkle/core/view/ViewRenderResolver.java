package org.agilej.sparkle.core.view;

import org.agilej.sparkle.core.ActionMethod;

/**
 * Get the appropriate ViewRender for action result.
 *
 * @see {@link SimpleViewRenderResolver}
 */
public interface ViewRenderResolver {

    ViewRender resolveViewRender(ActionMethod actionMethod, Object result);
    
}
