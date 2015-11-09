package org.agilej.sparkle.core.argument;


import org.agilej.sparkle.Cookie;
import org.agilej.sparkle.Cookies;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;
import org.agilej.sparkle.core.ActionMethodParameter;

/**
 * Argument resolver for param type with {@link Cookies}
 */
public class CookiesArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParameter actionMethodParameter) {
        return Cookies.class.equals(actionMethodParameter.paramType());
    }

    @Override
    public Object resolve(ActionMethodParameter actionMethodParameter, WebRequest request) {
        return new SimpleCookieManager(request, request.getWebResponse());
    }

    static class SimpleCookieManager implements Cookies{

        private final WebRequest request;
        private final WebResponse response;

        SimpleCookieManager(WebRequest request, WebResponse response){
            this.request = request;
            this.response = response;
        }

        @Override
        public Cookies addCookie(Cookie cookie) {
            this.response.addCookie(cookie);
            return this;
        }

        @Override
        public Cookies deleteCookie(Cookie cookie) {
            //TODO confirm
            cookie.maxAge(0);
            this.response.addCookie(cookie);
            return this;
        }

        @Override
        public Cookies deleteCookie(String cookieName) {
            Cookie cookie = cookie(cookieName);
            return deleteCookie(cookie);
        }

        @Override
        public Cookie cookie(String name) {
            Cookie[] cookies = request.cookies();
            for (Cookie cookie : cookies){
                if (name.equals(cookie.name())){
                    return cookie;
                }
            }
            return null;
        }
    }

}
