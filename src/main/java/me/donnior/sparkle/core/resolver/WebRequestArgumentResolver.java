package me.donnior.sparkle.core.resolver;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParameter;

/**
 * Argument resolver for argument with type {@link WebRequest}
 *
 */
public class WebRequestArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParameter actionParamDefinition) {
        return WebRequest.class.equals(actionParamDefinition.paramType());
    }

    @Override
    public Object resolve(ActionMethodParameter actionParamDefinition, WebRequest request) {
        return request;
    }

}
