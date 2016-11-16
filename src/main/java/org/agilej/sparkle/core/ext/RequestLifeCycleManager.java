package org.agilej.sparkle.core.ext;

import org.agilej.sparkle.mvc.ActionMethod;

public interface RequestLifeCycleManager {

    /**
     * check one handler whether deal with the response manually, for example use will directly write to a mvc response.
     * the framework will ignore the view rendering phase.
     * @param actionMethod
     * @return
     */
    boolean isResponseProcessedManually(ActionMethod actionMethod);
    
}
