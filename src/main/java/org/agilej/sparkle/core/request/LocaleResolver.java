package org.agilej.sparkle.core.request;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.annotation.UserConfigurable;

import java.util.Locale;

/**
 * resolver for set and get locale from request.
 * <br />
 *
 * sparkle framework can configured for different locale resolve strategy,
 * the built-in strategies are:
 *
 * <ul>
 *    <li> AcceptHeaderLocaleResolver - which resolve locale from http header </li>
 *    <li> CookieBasedLocaleResolver - which resolve locale from cookie value </li>
 *    <li> SessionBasedLocaleResolver - which resolve locale from session </li>
 * </ul>
 *
 */
@UserConfigurable
public interface LocaleResolver {

    Locale resolveLocale(WebRequest request);

    void setLocale(WebRequest request, Locale locale);

}
