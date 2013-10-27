package me.donnior.sparkle.core.resolver;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.Params;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ActionMethodParamDefinition;

/**
 * Argument resolver for argument  with type {@link Params} 
 *
 */
public class ParamInstanceArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionMethodParamDefinition actionParamDefinition) {
        return Params.class.equals(actionParamDefinition.paramType());
    }

    @Override
    public Object resovle(ActionMethodParamDefinition actionParamDefinition, WebRequest request) {
      return new HttpRequestParamsWraper((HttpServletRequest)request.getOriginalRequest());
    }

}
