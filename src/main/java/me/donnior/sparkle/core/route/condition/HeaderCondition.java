package me.donnior.sparkle.core.route.condition;

import me.donnior.sparkle.WebRequest;

public class HeaderCondition extends AbstractCondition {

    public HeaderCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(WebRequest webRequest, String key) {
        return webRequest.getHeader(key);
    }
    
}
