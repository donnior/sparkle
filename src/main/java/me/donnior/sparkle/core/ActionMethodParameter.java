package me.donnior.sparkle.core;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * represent one action method parameter.
 *
 * <p>
 * For example, consider the following controller with one action method called 'show'
 * </p>
 *
 * <pre><code>
 * public class ItemController{
 *     public String show(@PathVariable("id") String id){
 *         //...
 *     }
 * }
 * </code>
 * </pre>
 *
 * Now the 'show' action method has one ActionMethodParameter, its 'paramName' is 'id',
 * 'paramType' is 'String', and has one annotaion called 'PathVariable'
 *
 */
public interface ActionMethodParameter {

    String paramName();
    
    Class<?> paramType();
    
    List<Annotation> annotations();
    
    boolean hasAnnotation(Class<?> annotationType);

    Annotation getAnnotation(Class<?> annotationType);
    
}
