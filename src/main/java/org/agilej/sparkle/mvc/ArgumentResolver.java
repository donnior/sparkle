package org.agilej.sparkle.mvc;

import org.agilej.sparkle.WebRequest;

/**
 * ArgumentResolver is used to parse and set handler to controller's handler method. For example an
 * handler method has signature as :
 * <pre><code>
 * public String show(@Param("id") String id){
 *   //...
 * }
 * </code></pre>
 * 
 * here we have a handler 'id', the sparkle framework will resolve it from request and pass it to
 * this handler method automatically when this method is executing.
 * 
 * <br/>
 * <br/>
 * Sparkle have built-in ArgumentResolvers, and you can build your customized ArgumentResolver too.  
 * 
 * 
 */
public interface ArgumentResolver {

    /**
     * test whether current ArgumentResolver support a handler definition
     * @param actionMethodParameter
     * @return
     */
    boolean support(ActionMethodParameter actionMethodParameter);
    
    /**
     * 
     * resolve the handler value from request.
     * 
     * @param actionMethodParameter
     * @param request
     * @return
     */
    Object resolve(ActionMethodParameter actionMethodParameter, WebRequest request);
    
}
