package org.agilej.sparkle.mvc;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.List;

/**
 * represent one handler method of controller.
 *
 * <p>
 * For example, consider the following controller with one handler method called 'show'
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
 * Now the 'show' handler method's name is 'show', the return type is 'String'; and has one
 * ActionMethodParameter for the 'id' handler
 *
 * @see ActionMethodArgument
 */
public interface ActionMethod {

    /**
     * the handler method's name
     *
     * @return
     */
    String actionName();

    /**
     * the corresponding method for this handler
     *
     * @return
     */
    Method method();

    /**
     *
     * list for this handler method's parameters
     *
     * @return
     */
    List<ActionMethodArgument> parameters();

    /**
     * the annotation for this handler method
     * @return
     */
    List<Annotation> annotations();

    /**
     * test whether the handler method has given annotation
     *
     * @param annotationType
     * @return
     */
    boolean hasAnnotation(Class<?> annotationType);

    /**
     * the defined return type for this handler method.
     *
     * @return
     */
    Class<?> getReturnType();

}
