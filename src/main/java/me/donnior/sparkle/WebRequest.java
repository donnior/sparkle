package me.donnior.sparkle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface WebRequest {

    HttpServletRequest getServletRequest();
    
    HttpServletResponse getServletResponse();
    
}
