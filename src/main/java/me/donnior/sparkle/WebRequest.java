package me.donnior.sparkle;

import java.util.List;
import java.util.Map;


public interface WebRequest {
    
    public static final String REQ_ATTR_FOR_PATH_VARIABLES = "REQ_ATTR_FOR_PATH_VARIABLES";

    /**
     *
     * raw request
     * @param <T>
     * @return
     */
    <T> T getOriginalRequest();

    /**
     *
     * raw response
     *
     * @param <T>
     * @return
     */
    <T> T getOriginalResponse();

    /**
     * get associated WebResponse for this request
     * @return
     */
    WebResponse getWebResponse();


    /**
     * get param value for given key, if the given key has a String[] value, the first value will be returned
     *
     * @param key
     * @return
     */
    String getParameter(String key);

    /**
     * get param value for given key, if the given key has a String[] value, the first value will be returned
     * @param key
     * @return
     */
    default String params(String key){
        return getParameter(key);
    }

    /**
     * get header value for given key
     * @param key
     * @return
     */
    String getHeader(String key);

    /**
     * get http method for this request
     *
     * @return
     */
    String getMethod();

    /**
     * get path from request, without the 'context' path, 
     * for example, under a servlet container. 
     * @return
     */
    String getPath();

    /**
     *
     * get param values for given key
     *
     * @param paramName
     * @return
     */
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

    /**
     * get request body, especially for post
     */
    default String body(){
        return getBody();
    }

    /**
     * get attribute from request with given attribute name
     * @param attributeName
     * @param <T>
     * @return
     */
    <T> T getAttribute(String attributeName);

    default <T> T attr(String attributeName){
        return getAttribute(attributeName);
    }

    /**
     * set attribute to request
     * @param name
     * @param value
     */
    void setAttribute(String name, Object value);

    default void attr(String name, Object value){
        setAttribute(name, value);
    }

    List<Multipart> getMultiparts();

    /**
     * notify this request will be processed asynchronously
     */
    void startAsync();

    /**
     * whether this request is being processing asynchronously
     * @return
     */
    boolean isAsync();

    /**
     * complete async process
     */
    void completeAsync();

    /**
     * return path variable value for the given name according to the route definition.
     * @param name
     * @return
     */
    default String pathVariable(String name){
        Map<String, String> map = getAttribute(WebRequest.REQ_ATTR_FOR_PATH_VARIABLES);
        return map.get(name);
    }
}
