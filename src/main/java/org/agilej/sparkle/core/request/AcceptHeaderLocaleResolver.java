package org.agilej.sparkle.core.request;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.LocaleResolver;

import java.util.Locale;

/**
 * Locale resolver which get locale from http header.
 * Note this resolver can't set specific locale to request.
 */
public class AcceptHeaderLocaleResolver implements LocaleResolver {

    @Override
    public Locale resolveLocale(WebRequest request) {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public void setLocale(WebRequest request, Locale locale) {
        throw new UnsupportedOperationException(
            "Cannot change HTTP accept header - use a different locale resolution strategy");

    }

}
