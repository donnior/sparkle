package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;



public class ParamCondition extends AbstractCondition {

    public ParamCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(HttpServletRequest request, String key) {
        return request.getParameter(key);
    }
    
}
