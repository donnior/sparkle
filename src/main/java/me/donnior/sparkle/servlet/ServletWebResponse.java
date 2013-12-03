package me.donnior.sparkle.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.WebResponse;

public class ServletWebResponse implements WebResponse {

    private HttpServletResponse response;

    public ServletWebResponse(HttpServletResponse response) {
        this.response = response;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getOriginalResponse() {
        return (T)this.response;
    }
    
    @Override
    public void setStatus(int sc) {
        this.response.setStatus(sc);
    }

    @Override
    public void write(String string) {
        try {
            this.response.getWriter().write(string);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public void setHeader(String name, String value) {
        this.response.setHeader(name, value);
    }
    
    @Override
    public void setContentType(String type) {
        this.response.setContentType(type);
    }
     
    
}
