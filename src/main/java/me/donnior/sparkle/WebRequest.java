package me.donnior.sparkle;



public interface WebRequest {
    
    public static final String REQ_ATTR_FOR_PATH_VARIABLES = "REQ_ATTR_FOR_PATH_VARIABLES";

    <T> T getOriginalRequest();
    
    <T> T getOriginalResponse();

    String getParameter(String key);

    String getHeader(String key);

    String getMethod();

    /**
     * get path from request, without the 'context' path, 
     * for example, under a servlet container. 
     * @return
     */
    String getPath();

    String[] getParameterValues(String paramName);

    /**
     * get context path from request, for servlet container it's the servlet context path
     * @return
     */
    String getContextPath();

    WebResponse getWebResponse();
    
    <T> T getAttribute(String attributeName);

    void setAttribute(String name, Object value);
}
