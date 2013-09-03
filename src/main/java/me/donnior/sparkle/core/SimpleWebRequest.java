package me.donnior.sparkle.core;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.WebRequest;

public class SimpleWebRequest implements WebRequest{

    private final HttpServletResponse response;
    private final HttpServletRequest request;

    public SimpleWebRequest(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
    
    @Override
    public HttpServletRequest getServletRequest() {
        return this.request;
    }

    @Override
    public HttpServletResponse getServletResponse() {
        return this.response;
    }

}
