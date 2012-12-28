package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;



public class HeaderCondition extends AbstractCondition {

    public HeaderCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(HttpServletRequest request, String key) {
        return request.getHeader(key);
    }
    
}
