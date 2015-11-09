package org.agilej.web.adapter;

import java.util.List;

import org.agilej.sparkle.Multipart;
import org.agilej.sparkle.WebResponse;
import org.agilej.sparkle.core.request.AbstractWebRequest;

public class WebRequestAdapter extends AbstractWebRequest {

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
