package me.donnior.sparkle.core.resolver;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.core.ActionParamDefinition;

public interface ParamResolversManager {
    
    Object resolve(ActionParamDefinition paramDefinition, HttpServletRequest request);
    
    List<ArgumentResolver> registeredResolvers();

}
