package org.agilej.sparkle.core.execute;

import java.util.ArrayList;
import java.util.List;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.Interceptor;

/**
 * interceptor execution context for every request, because you should remember which interceptors are executed,
 * and make sure those executed interceptor must finish <pre><code>doAfterHandle</code></pre>
 */
public class InterceptorExecutionChain {

    private List<Interceptor> interceptors;
    private int interceptorIndex = -1;
    private volatile boolean allPassed = false;
    
    public InterceptorExecutionChain(List<? extends Interceptor> interceptors){
        this.interceptors = new ArrayList<Interceptor>();
        if(interceptors != null){
            this.interceptors.addAll(interceptors);
        }
    }
    
    public boolean doPreHandle(WebRequest request){
        for(Interceptor interceptor : this.interceptors){
            this.interceptorIndex++;
            boolean result = interceptor.preHandle(request, request.getWebResponse());
            if(!result){
                return false;
            }
        }
        this.allPassed = true;
        return this.allPassed;
    }
    
    
    public void doAfterHandle(WebRequest request){
        for(int i=this.interceptorIndex; i>=0; i--){
            Interceptor interceptor = this.interceptors.get(i);
            interceptor.afterHandle(request, request.getWebResponse());
        }
    }

    public boolean isAllPassed(){
        return this.allPassed;
    }

}
