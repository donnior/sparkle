package org.agilej.sparkle.mvc;

import org.agilej.sparkle.Interceptor;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.WebResponse;

public class FailInterceptor implements Interceptor {

    private boolean executed = false;
    private boolean cleaned = false;
    @Override
    public boolean preHandle(WebRequest request, WebResponse response) {
        executed = true;
        return false;
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
