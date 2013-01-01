package me.donnior.sparkle.internal;

import javax.servlet.http.HttpServletRequest;

public interface ParamResolver {
    
    Object resolve(ActionParamDefinition paramDefinition, HttpServletRequest request);

}
