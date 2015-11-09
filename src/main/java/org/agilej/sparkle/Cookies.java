package org.agilej.sparkle;

/**
 * cookie container, can be used to manage cookie read and write in request/response
 */
public interface Cookies {

    Cookies addCookie(Cookie cookie);

    Cookies deleteCookie(Cookie cookie);

    Cookies deleteCookie(String cookieName);

    Cookie cookie(String name);

}
