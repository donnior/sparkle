package me.donnior.sparkle.core.resolver;

import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.annotation.Param;
import me.donnior.sparkle.core.ActionParamDefinition;

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
    public Object resovle(ActionParamDefinition actionParamDefinition, WebRequest request) {
        //TODO introduce response as param or use simply solution?
        return request.getServletResponse();
    }

}
