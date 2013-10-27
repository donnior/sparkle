package me.donnior.sparkle.core.resolver;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParamDefinition;

/**
 * Argument resolver for argument with type HttpServletRequest 
 *
 */
public class HttpRequestArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParamDefinition actionParamDefinition) {
        return actionParamDefinition.paramType().equals(HttpServletRequest.class);
    }

    @Override
    public Object resovle(ActionMethodParamDefinition actionParamDefinition, WebRequest request) {
        return request.getOriginalRequest();
    }

}
