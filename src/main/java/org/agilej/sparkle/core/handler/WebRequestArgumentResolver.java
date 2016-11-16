package org.agilej.sparkle.core.handler;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.ActionMethodParameter;
import org.agilej.sparkle.mvc.ArgumentResolver;

/**
 * Argument resolver for handler with type {@link WebRequest}
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
