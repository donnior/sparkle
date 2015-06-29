package me.donnior.sparkle.engine;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethod;

public interface ViewRenderingPhaseExecutor {

    void doRenderViewPhase(WebRequest webRequest, Object controller, ActionMethod actionMethod, Object result);

}
