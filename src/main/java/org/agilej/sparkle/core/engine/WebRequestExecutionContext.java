package org.agilej.sparkle.core.engine;

import com.google.common.base.Stopwatch;
import org.agilej.sparkle.WebRequest;

import java.util.concurrent.TimeUnit;

public class WebRequestExecutionContext {

    private final WebRequest webRequest;
    private final InterceptorExecutionChain interceptorExecutionChain;
    private Stopwatch stopwatch = Stopwatch.createUnstarted();

    public WebRequestExecutionContext(WebRequest webRequest){
        this(webRequest, null);
    }

    public WebRequestExecutionContext(WebRequest webRequest, InterceptorExecutionChain ic) {
        this.webRequest = webRequest;
        this.interceptorExecutionChain = ic;
    }

    public void startTimer(String timerName){
        this.stopwatch.start();
    }

    public void endTimer(String timerName){
        this.stopwatch.stop();
        long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        stopwatch.reset();
    }

    public Stopwatch stopwatch(){
        return this.stopwatch;
    }

    public WebRequest webRequest() {
        return this.webRequest;
    }

    public InterceptorExecutionChain interceptorExecutionChain(){
        return this.interceptorExecutionChain;
    }

}
