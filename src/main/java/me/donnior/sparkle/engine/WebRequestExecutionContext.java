package me.donnior.sparkle.engine;

import com.google.common.base.Stopwatch;
import me.donnior.sparkle.WebRequest;

import java.util.concurrent.TimeUnit;

public class WebRequestExecutionContext {

    private final WebRequest webRequest;
    private Stopwatch stopwatch = Stopwatch.createUnstarted();

    public WebRequestExecutionContext(WebRequest webRequest){
        this.webRequest = webRequest;
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
}
