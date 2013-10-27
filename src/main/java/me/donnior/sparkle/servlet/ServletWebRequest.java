package me.donnior.sparkle.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.WebRequest;

public class ServletWebRequest implements WebRequest{

    private final HttpServletResponse response;
    private final HttpServletRequest request;

    public ServletWebRequest(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }
    
    @Override
    public String getPath() {
        String pathInfo = this.request.getPathInfo();
        if (pathInfo == null) {
            return this.request.getServletPath();   //wild servlet mapping like "/" or "*.do"
        } 
        return pathInfo;        //otherwise normal mapping like "/cms/*"
    }
    
    @Override
    public String getHeader(String name) {
        return this.request.getHeader(name);
    }
    
    @Override
    public String getParameter(String name) {
        return this.request.getParameter(name);
    }
    
    @Override
    public String getMethod() {
        return this.request.getMethod();
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public HttpServletRequest getOriginalRequest() {
        return this.request;
    }
    
    @Override
    public String[] getParameterValues(String paramName) {
        return this.request.getParameterValues(paramName);
    }

    @Override
    public HttpServletResponse getServletResponse() {
        return this.response;
    }

}
