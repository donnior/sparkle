package org.agilej.sparkle.core.dev;

import org.agilej.sparkle.Cookie;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CookiesFormatter {

    private final Cookie[] cookies;

    public CookiesFormatter(Cookie[] cookies){
        this.cookies = cookies;
    }

    public String format(){
        return Arrays.asList(this.cookies).stream().map(
                cookie -> cookie.name() + "=" + cookie.value()).collect(Collectors.joining("; "));
    }
}
