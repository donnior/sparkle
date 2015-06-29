package me.donnior.sparkle.engine;

import me.donnior.sparkle.core.ActionMethod;

public interface RequestLifeCycleManager {

    boolean isResponseProcessedManually(ActionMethod actionMethod);
    
}
