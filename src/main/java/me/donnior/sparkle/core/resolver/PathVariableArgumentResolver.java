package me.donnior.sparkle.core.resolver;

import java.lang.annotation.Annotation;
import java.util.Map;

import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.annotation.PathVariable;
import me.donnior.sparkle.core.ActionMethodParameter;
import me.donnior.sparkle.exception.SparkleException;
import me.donnior.sparkle.util.ParamResolveUtil;

/**
 * Argument resolver for argument annotated with {@link PathVariable} 
 *
 */
public class PathVariableArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParameter actionParamDefinition) {
        return actionParamDefinition.hasAnnotation(PathVariable.class);
    }

    @Override
    public Object resolve(ActionMethodParameter actionParamDefinition, WebRequest request) {
      FList<Annotation> ans =  FLists.create(actionParamDefinition.annotations());
      Annotation a = ans.find(new Predicate<Annotation>() {
          @Override
          public boolean apply(Annotation e) {
              return e.annotationType().equals(PathVariable.class);
          }
      });
      String paramName = ((PathVariable)a).value();
      Class<?> paramType = actionParamDefinition.paramType();
      
      //TODO how to get path variables with name from current request, 如何从RouteBuider中把解析的path variables传过来？
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
            throw new SparkleException("action method argument annotated with @Param not support primitive");
        }
        return null;
    }

}
