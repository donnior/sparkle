package org.agilej.sparkle.engine;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethod;

public interface ViewRenderingPhaseExecutor {

    void doRenderViewPhase(WebRequest webRequest, Object controller, ActionMethod actionMethod, Object result);

}
