package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import me.donnior.fava.FList;
import me.donnior.fava.Predict;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.annotation.Param;
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
    public Object resovle(ActionParamDefinition actionParamDefinition, HttpServletRequest request) {
      FList<Annotation> ans =  FLists.create(actionParamDefinition.annotions());
      Annotation a = ans.find(new Predict<Annotation>() {
        @Override
        public boolean apply(Annotation e) {
            return e.annotationType().equals(Param.class);
        }
      });
      String paramName = ((Param)a).value();
      Class<?> paramType = actionParamDefinition.paramType();
      
      String[] values = request.getParameterValues(paramName);
      if(values == null){
          return nullValueForType(paramType);
      } else {
          return new ParamResolveUtil().convertValue(values, paramType);
      }
      
      //TODO add class cast for supported type like String, int; but exceptioin for customized type.
    }

    private Object nullValueForType(Class<?> type) {
        if(type.isPrimitive()){
            throw new RuntimeException("action method argument annotated with @Param not support primitive");
        }
        return null;
    }

}
