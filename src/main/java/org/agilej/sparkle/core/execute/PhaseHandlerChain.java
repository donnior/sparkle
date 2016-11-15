package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.core.annotation.Singleton;
import org.agilej.sparkle.core.config.ConfigResult;

import java.util.ArrayList;
import java.util.List;

@Singleton
public class PhaseHandlerChain {

    private List<PhaseHandler> handlers = new ArrayList<>();
    private PhaseHandler startPhaseHandler;

    public PhaseHandlerChain(PhaseHandler startPhaseHandler){
        this.startPhaseHandler = startPhaseHandler;
    }

    public void startPhaseHandle(WebRequestExecutionContext context){
        this.startPhaseHandler.handle(context);
    }

    public PhaseHandler firstHandler() {
        return this.startPhaseHandler;
    }

}
