package me.donnior.sparkle.engine;

import java.util.ArrayList;
import java.util.List;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.interceptor.Interceptor;

/**
 * interceptor execution context for every request, because you should remember which interceptors are executed,
 * and make sure those executed interceptor must finish <pre><code>doAfterHandle</code></pre>
 */
public class InterceptorExecutionChain {

    private List<Interceptor> interceptors;
    private int interceptorIndex = -1;
    
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
        return true;
    }
    
    
    public void doAfterHandle(WebRequest request){
        for(int i=this.interceptorIndex; i>=0; i--){
            Interceptor interceptor = this.interceptors.get(i);
            interceptor.afterHandle(request, request.getWebResponse());
        }
    }
    
}
