package org.agilej.sparkle.interceptor;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;

public class ExceptionInterceptor implements Interceptor {

    private boolean executed = false;
    private boolean cleaned = false;
    @Override
    public boolean preHandle(WebRequest request, WebResponse response) {
        executed = true;
        throw new RuntimeException("test runtime exception in interceptor");
    }

    @Override
    public void afterHandle(WebRequest request, WebResponse response) {
        this.cleaned = true;
    }

    public boolean isExecuted() {
        return this.executed;
    }

    public boolean isCleaned(){
        return this.cleaned;
    }
}
