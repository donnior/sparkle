package org.agilej.sparkle.core.argument;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethodParameter;

/**
 * ArgumentResolver is used to parse and set argument to controller's action method. For example an
 * action method has signature as :
 * <pre><code>
 * public String show(@Param("id") String id){
 *   //...
 * }
 * </code></pre>
 * 
 * here we have a argument 'id', the sparkle framework will resolve it from request and pass it to
 * this action method automatically when this method is executing.
 * 
 * <br/>
 * <br/>
 * Sparkle have built-in ArgumentResolvers, and you can build your customized ArgumentResolver too.  
 * 
 * 
 */
public interface ArgumentResolver {

    /**
     * test whether current ArguentResolver support a argument definition
     * @param actionMethodParameter
     * @return
     */
    boolean support(ActionMethodParameter actionMethodParameter);
    
    /**
     * 
     * resolve the argument value from request.
     * 
     * @param actionMethodParameter
     * @param request
     * @return
     */
    Object resolve(ActionMethodParameter actionMethodParameter, WebRequest request);
    
}
