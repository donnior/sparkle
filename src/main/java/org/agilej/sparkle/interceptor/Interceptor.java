package org.agilej.sparkle.interceptor;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;

/**
 * interceptor is for doing pre and after work when processing one request.
 */
public interface Interceptor {

    boolean preHandle(WebRequest request, WebResponse response);
    
    void afterHandle(WebRequest request, WebResponse response);
    
}
