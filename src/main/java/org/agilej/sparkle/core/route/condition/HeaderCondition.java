package org.agilej.sparkle.core.route.condition;

import org.agilej.sparkle.WebRequest;

public class HeaderCondition extends AbstractCondition {

    public HeaderCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(WebRequest webRequest, String key) {
        return webRequest.getHeader(key);
    }
    
}
