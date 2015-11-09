package org.agilej.web.adapter;

public class GetWebRequest extends WebRequestAdapter{

    private String url;

    public GetWebRequest(String url) {
        this.url = url;
    }
    
    @Override
    public String getPath() {
        return this.url;
    }
    
    @Override
    public String getMethod() {
        return "get";
    }
}
