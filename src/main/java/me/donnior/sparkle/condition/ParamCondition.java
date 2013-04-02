package me.donnior.sparkle.condition;

import javax.servlet.http.HttpServletRequest;

public class ParamCondition extends AbstractCondition {

    public ParamCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }
    
}
