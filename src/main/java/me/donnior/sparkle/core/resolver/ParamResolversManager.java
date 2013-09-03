package me.donnior.sparkle.core.resolver;

import java.util.List;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParamDefinition;

public interface ParamResolversManager {
    
    Object resolve(ActionMethodParamDefinition paramDefinition, WebRequest request);
    
    List<ArgumentResolver> registeredResolvers();

}
