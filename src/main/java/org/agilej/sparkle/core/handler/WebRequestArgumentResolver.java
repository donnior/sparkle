package org.agilej.sparkle.core.handler;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.ActionMethodArgument;
import org.agilej.sparkle.mvc.ArgumentResolver;

/**
 * Argument resolver for handler with type {@link WebRequest}
 *
 */
public class WebRequestArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodArgument actionMethodParameter) {
        return WebRequest.class.equals(actionMethodParameter.paramType());
    }

    @Override
    public Object resolve(ActionMethodArgument actionMethodParameter, WebRequest request) {
        return request;
    }

}
