package me.donnior.sparkle.core.resolver;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ParamResolversManager {
    
    Object resolve(ActionParamDefinition paramDefinition, HttpServletRequest request);
    
    List<ArgumentResolver> registeredResolvers();

}
