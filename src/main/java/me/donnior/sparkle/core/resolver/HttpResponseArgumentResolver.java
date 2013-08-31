package me.donnior.sparkle.core.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.annotation.Param;

/**
 * Argument resolver for argument annotated with {@link Param} 
 *
 */
public class HttpResponseArgumentResolver implements ArgumentResolver {

    @Override
    public boolean support(ActionParamDefinition actionParamDefinition) {
        return actionParamDefinition.paramType().equals(HttpServletResponse.class);
    }

    @Override
    public Object resovle(ActionParamDefinition actionParamDefinition, HttpServletRequest request) {
        //TODO introduce response as param or use simply solution?
        throw new RuntimeException("not support HttpServletResponse param now");
    }

}
