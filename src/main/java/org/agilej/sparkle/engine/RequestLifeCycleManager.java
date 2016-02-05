package org.agilej.sparkle.engine;

import org.agilej.sparkle.core.action.ActionMethod;

public interface RequestLifeCycleManager {

    /**
     * check one action whether deal with the response manually, for example use will directly write to a servlet response.
     * the framework will ignore the view rendering phase.
     * @param actionMethod
     * @return
     */
    boolean isResponseProcessedManually(ActionMethod actionMethod);
    
}
