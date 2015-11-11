package org.agilej.sparkle;

import java.io.Writer;

/**
 * interface for presenting a web response
 */
//TODO should be introduced?
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
    
    void setContentType(String type);

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
