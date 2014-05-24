package me.donnior.sparkle;



public interface WebRequest {
    
    public static final String REQ_ATTR_FOR_PATH_VARIABLES = "REQ_ATTR_FOR_PATH_VARIABLES";

    <T> T getOriginalRequest();
    
    <T> T getOriginalResponse();

    String getParameter(String key);

    String getHeader(String key);

    String getMethod();

    String getPath();

    String[] getParameterValues(String paramName);

    String getContextPath();

    WebResponse getWebResponse();
    
    <T> T getAttribute(String attributeName);

    void setAttribute(String name, Object value);
}
