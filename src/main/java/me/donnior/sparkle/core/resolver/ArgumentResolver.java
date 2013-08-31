package me.donnior.sparkle.core.resolver;

import javax.servlet.http.HttpServletRequest;

public interface ArgumentResolver {

    boolean support(ActionParamDefinition actionParamDefinition);
    
    Object resovle(ActionParamDefinition actionParamDefinition, HttpServletRequest request);
    
}
