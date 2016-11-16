package org.agilej.sparkle.core.handler;

import java.lang.annotation.Annotation;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.annotation.Param;
import org.agilej.sparkle.mvc.ActionMethodParameter;
import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.mvc.ArgumentResolver;
import org.agilej.sparkle.util.ParamResolveUtil;

/**
 * Argument resolver for handler annotated with {@link Param}
 */
public class ParamAnnotationArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParameter actionMethodParameter) {
        return actionMethodParameter.hasAnnotation(Param.class);
    }

    @Override
    public Object resolve(ActionMethodParameter actionMethodParameter, WebRequest request) {
//      FList<Annotation> ans =  FLists.create(parameter.annotations());
//      Annotation a = ans.find(new Predicate<Annotation>() {
//          @Override
//          public boolean apply(Annotation e) {
//              return e.annotationType().equals(Param.class);
//          }
//      });
      Annotation a = actionMethodParameter.getAnnotation(Param.class);
      String paramName = ((Param)a).value();
      Class<?> paramType = actionMethodParameter.paramType();
      
      String[] values = request.getParameterValues(paramName);
      if(values == null){
          return nullValueForType(paramType);
      } else {
          return ParamResolveUtil.convertValue(values, paramType);
      }
      
      //TODO add class cast for supported type like String, int; but exception for customized type.
    }

    private Object nullValueForType(Class<?> type) {
        if(type.isPrimitive()){
            throw new SparkleException("handler method handler annotated with @Param not support primitive");
        }
        return null;
    }

}
