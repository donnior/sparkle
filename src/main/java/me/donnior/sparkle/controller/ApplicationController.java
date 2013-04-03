package me.donnior.sparkle.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApplicationController {
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    
    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }
    
    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }
}
