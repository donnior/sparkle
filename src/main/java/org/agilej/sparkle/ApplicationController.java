package org.agilej.sparkle;


public abstract class ApplicationController {
    
    protected WebRequest request;
    protected WebResponse response;
    
    public void setRequest(WebRequest request) {
        this.request = request;
    }
    
    public void setResponse(WebResponse response) {
        this.response = response;
    }
}
