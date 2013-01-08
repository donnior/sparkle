package me.donnior.sparkle.internal;

import javax.servlet.http.HttpServletRequest;

public interface ParamResolversManager {
    
    Object resolve(ActionParamDefinition paramDefinition, HttpServletRequest request);

}
