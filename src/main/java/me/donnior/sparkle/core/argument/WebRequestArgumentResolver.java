package me.donnior.sparkle.core.argument;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParameter;
import me.donnior.sparkle.core.argument.ArgumentResolver;

/**
 * Argument resolver for argument with type {@link WebRequest}
 *
 */
public class WebRequestArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParameter actionMethodParameter) {
        return WebRequest.class.equals(actionMethodParameter.paramType());
    }

    @Override
    public Object resolve(ActionMethodParameter actionMethodParameter, WebRequest request) {
        return request;
    }

}
