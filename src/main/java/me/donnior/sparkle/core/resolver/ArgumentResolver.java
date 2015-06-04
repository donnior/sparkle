package me.donnior.sparkle.core.resolver;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParamDefinition;

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
     * @param actionParamDefinition
     * @return
     */
    boolean support(ActionMethodParamDefinition actionParamDefinition);
    
    /**
     * 
     * resolve the argument value from request.
     * 
     * @param actionParamDefinition
     * @param request
     * @return
     */
    Object resolve(ActionMethodParamDefinition actionParamDefinition, WebRequest request);
    
}
