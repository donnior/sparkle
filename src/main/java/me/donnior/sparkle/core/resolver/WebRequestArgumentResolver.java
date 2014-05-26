package me.donnior.sparkle.core.resolver;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParamDefinition;

/**
 * Argument resolver for argument with type WebRequest 
 *
 */
public class WebRequestArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParamDefinition actionParamDefinition) {
        return actionParamDefinition.paramType().equals(WebRequest.class);
    }

    @Override
    public Object resovle(ActionMethodParamDefinition actionParamDefinition, WebRequest request) {
        return request;
    }

}
