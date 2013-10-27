package me.donnior.sparkle.servlet;

import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.WebResponse;

public class ServletWebResponse implements WebResponse {

    private HttpServletResponse response;

    public ServletWebResponse(HttpServletResponse response) {
        this.response = response;
    }

    @Override
    public <T> T getOriginalResponse() {
        return (T)this.response;
    }
    
    @Override
    public void setStatus(int sc) {
        this.response.setStatus(sc);
    }

}
