package me.donnior.sparkle.internal;

import javax.servlet.http.HttpServletRequest;

public class DefaultParamResolver implements ParamResolver {

    @Override
    public Object resolve(ActionParamDefinition paramDefinition,
            HttpServletRequest request) {
        if(paramDefinition.paramType().equals(String.class)){
            return request.getParameter("a");
//            return request.getParameter(paramDefinition.paramName());
        }
        return null;
    }

}
