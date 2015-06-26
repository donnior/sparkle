package me.donnior.sparkle.engine;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodDefinition;

public interface ViewRenderingPhaseExecutor {

    void doRenderViewPhase(WebRequest webRequest, Object controller, ActionMethodDefinition adf, Object result);

}
