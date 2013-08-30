package me.donnior.sparkle.internal;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.annotation.Param;

/**
 * Argument resolver for argument annotated with {@link Param} 
 *
 */
public class HttpRequestArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionParamDefinition actionParamDefinition) {
        return actionParamDefinition.paramType().equals(HttpServletRequest.class);
    }

    @Override
    public Object resovle(ActionParamDefinition actionParamDefinition, HttpServletRequest request) {
        return request;
    }

}
