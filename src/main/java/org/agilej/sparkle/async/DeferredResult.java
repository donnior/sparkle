package org.agilej.sparkle.async;

/**
 * DeferredResult provides an alternative to using a Callable for asynchronous request processing.
 * While a Callable is executed concurrently on behalf of the application,
 * with a DeferredResult the application can produce the result from a thread of its choice.
 */
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
