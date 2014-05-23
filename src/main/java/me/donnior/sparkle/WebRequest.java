package me.donnior.sparkle;


public interface WebRequest {

    <T> T getOriginalRequest();
    
    <T> T getOriginalResponse();

    String getParameter(String key);

    String getHeader(String key);

    String getMethod();

    String getPath();

    String[] getParameterValues(String paramName);

    String getContextPath();

    WebResponse getWebResponse();
    
}
