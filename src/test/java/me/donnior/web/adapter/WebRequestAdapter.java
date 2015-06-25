package me.donnior.web.adapter;

import java.util.List;

import me.donnior.sparkle.Multipart;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.WebResponse;

public class WebRequestAdapter implements WebRequest{

    @Override
    public <T> T getOriginalRequest() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T getOriginalResponse() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getParameter(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getHeader(String key) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getMethod() {
        return "get";
    }

    @Override
    public String getPath() {
        return "";
    }

    @Override
    public String[] getParameterValues(String paramName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getContextPath() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public WebResponse getWebResponse() {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public <T> T getAttribute(String attributeName) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public void setAttribute(String name, Object value) {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public String getBody() {
        return null;
    }

    @Override
    public List<Multipart> getMultiparts() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAsync() {
        return false;
    }

    @Override
    public void startAsync() {

    }

    @Override
    public void completeAsync() {

    }
}
