package org.agilej.sparkle.core.handler;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.annotation.SessionAttribute;
import org.agilej.sparkle.mvc.ActionMethodParameter;
import org.agilej.sparkle.mvc.ArgumentResolver;

import java.lang.annotation.Annotation;

/**
 * Argument resolver for handler annotated with {@link SessionAttribute}
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
