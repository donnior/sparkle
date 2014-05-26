package me.donnior.sparkle.interceptor;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.WebResponse;

public interface Interceptor {

    boolean preHandle(WebRequest request, WebResponse response);
    
    void afterHandle(WebRequest request, WebResponse response);
    
}
