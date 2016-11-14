package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.core.WebRequestExecutionContext;

public class EndLoopHandler extends AbstractPhaseHandler {

    @Override
    public void handle(WebRequestExecutionContext context) {
        this.postHandle(context);
    }


}
