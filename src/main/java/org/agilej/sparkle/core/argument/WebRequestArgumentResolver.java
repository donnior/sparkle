package org.agilej.sparkle.core.argument;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethodParameter;

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
