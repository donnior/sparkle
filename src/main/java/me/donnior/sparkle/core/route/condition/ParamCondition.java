package me.donnior.sparkle.core.route.condition;

import me.donnior.sparkle.WebRequest;


public class ParamCondition extends AbstractCondition {

    public ParamCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(WebRequest request, String key) {
        return request.getParameter(key);
    }
    
}
