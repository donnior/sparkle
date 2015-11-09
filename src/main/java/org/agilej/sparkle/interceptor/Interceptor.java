package org.agilej.sparkle.interceptor;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;

public interface Interceptor {

    boolean preHandle(WebRequest request, WebResponse response);
    
    void afterHandle(WebRequest request, WebResponse response);
    
}
