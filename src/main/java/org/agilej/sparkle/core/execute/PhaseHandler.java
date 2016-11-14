package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.core.annotation.Singleton;

@Singleton
public interface PhaseHandler {

    /**
     * next phase handler to pass
     * @return
     */
    PhaseHandler next();

    /**
     * previous phase handler to pass
     * @return
     */
    PhaseHandler previous();

    /**
     * execution method
     * @param context
     */
    void handle(WebRequestExecutionContext context);

    /**
     * execution method after processing
     * @param context
     */
    void postHandle(WebRequestExecutionContext context);

}
