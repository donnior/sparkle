package me.donnior.sparkle.internal;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.Params;

/**
 * Argument resolver for argument  with type {@link Params} 
 *
 */
public class ParamInstanceArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionParamDefinition actionParamDefinition) {
        return Params.class.equals(actionParamDefinition.paramType());
    }

    @Override
    public Object resovle(ActionParamDefinition actionParamDefinition, HttpServletRequest request) {
      return (Params)new HttpRequestParamsWraper(request);
    }

}
