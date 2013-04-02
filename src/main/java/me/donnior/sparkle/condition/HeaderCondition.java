package me.donnior.sparkle.condition;

import javax.servlet.http.HttpServletRequest;

public class HeaderCondition extends AbstractCondition {

    public HeaderCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(HttpServletRequest request, String key) {
        return request.getHeader(key);
    }
    
}
