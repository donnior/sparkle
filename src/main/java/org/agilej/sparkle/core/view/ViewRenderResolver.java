package org.agilej.sparkle.core.view;

import org.agilej.sparkle.mvc.ActionMethod;
import org.agilej.sparkle.mvc.ViewRender;

/**
 * Get the appropriate ViewRender for handler result.
 *
 * @see {@link SimpleViewRenderResolver}
 */
public interface ViewRenderResolver {

    ViewRender resolveViewRender(ActionMethod actionMethod, Object result);
    
}
