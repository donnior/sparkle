package org.agilej.sparkle.core;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * represent one action method of controller.
 *
 * <p>
 * For example, consider the following controller with one action method called 'show'
 * </p>
 *
 * <pre>
 * {@code
 *
 * public class ItemController{
 *
 *     @literal@Json
 *     public String show(String id){
 *         //...
 *     }
 * }
 * }
 *
 * </pre>
 *
 * Now the 'show' action method's name is 'show', the return type is 'String'; and has one
 * ActionMethodParameter for the 'id' argument
 *
 * @see ActionMethodParameter
 */
public interface ActionMethod {

    /**
     * the action method's name
     *
     * @return
     */
    String actionName();

    /**
     * the corresponding method for this action
     *
     * @return
     */
    Method method();

    /**
     *
     * list for this action method's parameters
     *
     * @return
     */
    List<ActionMethodParameter> parameters();

    /**
     * the annotation for this action method
     * @return
     */
    List<Annotation> annotations();

    /**
     * test whether the action method has given annotation
     *
     * @param annotationType
     * @return
     */
    boolean hasAnnotation(Class<?> annotationType);

    /**
     * the defined return type for this action method.
     *
     * @return
     */
    public Class<?> getReturnType();

}
