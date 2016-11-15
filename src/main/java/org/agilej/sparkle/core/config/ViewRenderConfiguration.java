package org.agilej.sparkle.core.config;

import org.agilej.fava.FList;
import org.agilej.sparkle.core.view.ViewRender;

public interface ViewRenderConfiguration {

    FList<Class<? extends ViewRender>> getCustomizedViewRenders();

}
