package org.agilej.sparkle.core.request;

import org.agilej.sparkle.WebRequest;

import java.util.Locale;

/**
 * Locale resolver which can get and set locale from cookie value.
 *
 */
public class CookieBasedLocaleResolver implements LocaleResolver {

    private static final String DEFAULT_LOCALE_NAME = "_locale";

    private final String localeFiledName;

    /**
     *  create instance with default field name
     */
    public CookieBasedLocaleResolver(){
        this(DEFAULT_LOCALE_NAME);
    }

    /**
     * the field name will used to store locale value in cookie
     * @param localeFieldName
     */
    public CookieBasedLocaleResolver(String localeFieldName) {
        this.localeFiledName = localeFieldName;
    }


    @Override
    public Locale resolveLocale(WebRequest request) {
        throw new RuntimeException("not implemented yet");
    }

    @Override
    public void setLocale(WebRequest request, Locale locale) {
        throw new RuntimeException("not implemented yet");
    }
}
