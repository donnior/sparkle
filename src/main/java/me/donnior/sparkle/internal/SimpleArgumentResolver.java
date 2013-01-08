package me.donnior.sparkle.internal;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;

import me.donnior.fava.FList;
import me.donnior.fava.Predict;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.annotation.Param;

/**
 * Argument resolver for argument annotated with {@link Param} 
 *
 */
public class SimpleArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionParamDefinition actionParamDefinition) {
        return actionParamDefinition.hasAnnotation(Param.class);
//        return actionParamDefinition.paramType().equals(String.class);
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
      return request.getParameter(((Param)a).value());    
      //TODO add class cast for supported type like String, int; but exceptioin for customized type.
    }

}
