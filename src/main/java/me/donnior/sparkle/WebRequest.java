package me.donnior.sparkle;

import java.util.List;



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
    
    /**
     * get request body, especially for post 
     */
    String getBody();
    

    WebResponse getWebResponse();
    
    <T> T getAttribute(String attributeName);

    void setAttribute(String name, Object value);
    
    List<Multipart> getMultiparts();

    void startAsync();

    boolean isAsync();

    void completeAsync();
}
