package org.agilej.sparkle;

import java.io.Writer;

/**
 * interface for presenting a web response
 */
public interface WebResponse {

    /**
     *
     * raw response
     *
     * @return the raw response object provided by runtime container, such as a servlet response or netty response
     */
    <T> T getOriginalResponse();

    /**
     * set status
     * @param statusCode
     */
    void setStatus(int statusCode);

    /**
     * write string content to response
     * @param content
     */
    void write(String content);

    /**
     * set response header
     * @param name
     * @param value
     */
    void setHeader(String name, String value);

    /**
     * alias for {@link #setHeader(String, String)}
     * @see #setHeader(String, String)
     * @param name
     * @param value
     */
    default void header(String name, String value) {
        setHeader(name, value);
    }
    
    void setContentType(String type);

    /**
     * alias for {@link #setContentType(String)}
     *
     * @see  #setContentType(String)
     * @param type
     */
    default void contentType(String type) {
        this.setContentType(type);
    }
    /**
     * return associated writer to this response
     * @return
     */
    Writer getWriter();

    /**
     * add cookie to response
     * @param cookie
     */
    default void addCookie(Cookie cookie){

    }

}
