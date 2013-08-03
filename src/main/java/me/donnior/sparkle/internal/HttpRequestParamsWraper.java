package me.donnior.sparkle.internal;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.Params;

public class HttpRequestParamsWraper implements Params {

    private HttpServletRequest request;

    public HttpRequestParamsWraper(HttpServletRequest request) {
        this.request = request;
    }

    @Override
    public String get(String name) {
        return request.getParameter(name);
    }

    @Override
    public String[] gets(String name) {
        return request.getParameterValues(name);
    }

    @Override
    public <T> T get(String name, Class<T> clz) {
        return null;
    }

}
