package org.agilej.sparkle.core.argument;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.annotation.SessionAttribute;
import org.agilej.sparkle.core.action.ActionMethodParameter;

import java.lang.annotation.Annotation;

/**
 * Argument resolver for argument annotated with {@link SessionAttribute}
 *
 */
public class SessionAttributeArgumentResolver implements ArgumentResolver {
    @Override
    public boolean support(ActionMethodParameter actionMethodParameter) {
        return actionMethodParameter.hasAnnotation(SessionAttribute.class);
    }

    @Override
    public Object resolve(ActionMethodParameter actionMethodParameter, WebRequest request) {
        Annotation a = actionMethodParameter.getAnnotation(SessionAttribute.class);
        String sessionAttributeName = ((SessionAttribute)a).value();
        return request.session().get(sessionAttributeName);
    }
}
