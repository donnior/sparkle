package org.agilej.sparkle.async;

public class DeferredResult {
    private Object result;
    private DeferredResultHandler resultHandler;

    public void setResult(Object result) {
        synchronized (this) {
            if (this.result != null) {
                throw new RuntimeException("Can't set result twice for DeferredResult ");
            }
            this.result = result;
        }

    }

    public void setResultHandler(DeferredResultHandler handler) {
        this.resultHandler = handler;
    }

    public interface  DeferredResultHandler {
        void handleResult(Object result);
    }
}
