package me.donnior.sparkle.engine;

import me.donnior.sparkle.core.ActionMethodDefinition;

public interface RequestLifeCycleManager {

    boolean isResponseProcessedManually(ActionMethodDefinition adf);
    
}
