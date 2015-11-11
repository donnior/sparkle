package org.agilej.sparkle.core.argument;

import java.lang.annotation.Annotation;
import java.util.Map;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.annotation.PathVariable;
import org.agilej.sparkle.core.ActionMethodParameter;
import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.util.ParamResolveUtil;
import org.agilej.fava.FList;
import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;

/**
 * Argument resolver for argument annotated with {@link PathVariable}
 *
 */
public class PathVariableArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParameter actionMethodParameter) {
        return actionMethodParameter.hasAnnotation(PathVariable.class);
    }

    @Override
    public Object resolve(ActionMethodParameter actionMethodParameter, WebRequest request) {
      FList<Annotation> ans =  FLists.create(actionMethodParameter.annotations());
      Annotation a = ans.find(new Predicate<Annotation>() {
          @Override
          public boolean apply(Annotation e) {
              return e.annotationType().equals(PathVariable.class);
          }
      });
      String paramName = ((PathVariable)a).value();
      Class<?> paramType = actionMethodParameter.paramType();
      
      //TODO how to get path variables with name from current request
      Map<String, String> pathVariables = request.getAttribute(WebRequest.REQ_ATTR_FOR_PATH_VARIABLES);
      String[] values = new String[]{pathVariables.get(paramName)};
      if(values == null){
          return nullValueForType(paramType);
      } else {
          return new ParamResolveUtil().convertValue(values, paramType);
      }
      
      //TODO add class cast for supported type like String, int; but exception for customized type.
    }

    private Object nullValueForType(Class<?> type) {
        if(type.isPrimitive()){
            throw new SparkleException(
                    "action method argument annotated with @Param not support primitive");
        }
        return null;
    }

}
