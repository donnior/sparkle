package me.donnior.sparkle.core.route.condition;

import me.donnior.sparkle.WebRequest;

public class ConsumeCondition extends AbstractCondition {

    public ConsumeCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(WebRequest request, String key) {
//        return request.getHeader(key);
        throw new UnsupportedOperationException("not implemented yet");
    }
    
}
