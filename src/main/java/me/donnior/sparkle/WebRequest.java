package me.donnior.sparkle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebRequest {

    HttpServletRequest getServletRequest();
    
    HttpServletResponse getServletResponse();

    String getParameter(String key);

    String getHeader(String key);

    String getMethod();
    
}
