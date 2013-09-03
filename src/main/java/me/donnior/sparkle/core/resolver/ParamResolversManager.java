package me.donnior.sparkle.core.resolver;

import java.util.List;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionParamDefinition;

public interface ParamResolversManager {
    
    Object resolve(ActionParamDefinition paramDefinition, WebRequest request);
    
    List<ArgumentResolver> registeredResolvers();

}
