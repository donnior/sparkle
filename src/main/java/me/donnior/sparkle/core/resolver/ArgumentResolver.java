package me.donnior.sparkle.core.resolver;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParamDefinition;

public interface ArgumentResolver {

    boolean support(ActionMethodParamDefinition actionParamDefinition);
    
    Object resovle(ActionMethodParamDefinition actionParamDefinition, WebRequest request);
    
}
