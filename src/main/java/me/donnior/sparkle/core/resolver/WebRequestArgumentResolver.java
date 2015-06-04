package me.donnior.sparkle.core.resolver;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParamDefinition;

/**
 * Argument resolver for argument with type {@link WebRequest}
 *
 */
public class WebRequestArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParamDefinition actionParamDefinition) {
        return WebRequest.class.equals(actionParamDefinition.paramType());
    }

    @Override
    public Object resolve(ActionMethodParamDefinition actionParamDefinition, WebRequest request) {
        return request;
    }

}
