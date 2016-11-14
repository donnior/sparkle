package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.interceptor.Interceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class InterceptorsHandler extends AbstractPhaseHandler {

    private List<Interceptor> interceptors;

    private final static Logger logger = LoggerFactory.getLogger(InterceptorsHandler.class);

    @Override
    public void handle(WebRequestExecutionContext context) {
        InterceptorExecutionChain ic = new InterceptorExecutionChain(this.interceptors);
        context.setInterceptorExecutionChain(ic);

        WebRequest webRequest = context.webRequest();
        boolean interceptorPassed = context.interceptorExecutionChain().doPreHandle(webRequest);
        if(!interceptorPassed){
            logger.info("Interceptors execute failed, request processing ignored.");
            this.postHandle(context);
        } else {
            forwardToNext(context);
        }
    }

    @Override
    public void postHandle(WebRequestExecutionContext context) {
        context.interceptorExecutionChain().doAfterHandle(context.webRequest());
    }

    public void setInterceptors(List<Interceptor> interceptors) {
        this.interceptors = interceptors;
    }
}
