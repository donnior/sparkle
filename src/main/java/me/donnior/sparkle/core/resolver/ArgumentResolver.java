package me.donnior.sparkle.core.resolver;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionParamDefinition;

public interface ArgumentResolver {

    boolean support(ActionParamDefinition actionParamDefinition);
    
    Object resovle(ActionParamDefinition actionParamDefinition, WebRequest request);
    
}
