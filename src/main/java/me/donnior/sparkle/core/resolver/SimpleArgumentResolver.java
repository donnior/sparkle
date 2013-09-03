package me.donnior.sparkle.core.resolver;

import java.lang.annotation.Annotation;

import me.donnior.fava.FList;
import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.annotation.Param;
import me.donnior.sparkle.core.ActionParamDefinition;
import me.donnior.sparkle.exception.SparkleException;
import me.donnior.sparkle.util.ParamResolveUtil;

/**
 * Argument resolver for argument annotated with {@link Param} 
 *
 */
public class SimpleArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionParamDefinition actionParamDefinition) {
        return actionParamDefinition.hasAnnotation(Param.class);
    }

    @Override
    public Object resovle(ActionParamDefinition actionParamDefinition, WebRequest request) {
      FList<Annotation> ans =  FLists.create(actionParamDefinition.annotions());
      Annotation a = ans.find(new Predicate<Annotation>() {
          @Override
          public boolean apply(Annotation e) {
              return e.annotationType().equals(Param.class);
          }
      });
      String paramName = ((Param)a).value();
      Class<?> paramType = actionParamDefinition.paramType();
      
      String[] values = request.getServletRequest().getParameterValues(paramName);
      if(values == null){
          return nullValueForType(paramType);
      } else {
          return new ParamResolveUtil().convertValue(values, paramType);
      }
      
      //TODO add class cast for supported type like String, int; but exceptioin for customized type.
    }

    private Object nullValueForType(Class<?> type) {
        if(type.isPrimitive()){
            throw new SparkleException("action method argument annotated with @Param not support primitive");
        }
        return null;
    }

}
