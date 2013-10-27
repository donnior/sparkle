package me.donnior.sparkle;

import javax.servlet.http.HttpServletResponse;

public interface WebRequest {

    <T> T getOriginalRequest();
    
    HttpServletResponse getServletResponse();

    String getParameter(String key);

    String getHeader(String key);

    String getMethod();

    String getPath();

    String[] getParameterValues(String paramName);
    
}
