package me.donnior.sparkle.internal;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

public interface ParamResolversManager {
    
    Object resolve(ActionParamDefinition paramDefinition, HttpServletRequest request);
    
    List<ArgumentResolver> registeredResolvers();

}
