package me.donnior.sparkle.core.resolver;

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
      return (Params)new HttpRequestParamsWraper(request.getServletRequest());
    }

}
