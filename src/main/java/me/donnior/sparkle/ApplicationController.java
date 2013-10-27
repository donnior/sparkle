package me.donnior.sparkle;

import javax.servlet.http.HttpServletResponse;

public class ApplicationController {
    
    protected WebRequest request;
    protected HttpServletResponse response;
    
    public void setRequest(WebRequest request) {
        this.request = request;
    }
    
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
