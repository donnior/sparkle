package me.donnior.sparkle.condition;

import javax.servlet.http.HttpServletRequest;

public class ConsumeCondition extends AbstractCondition {

    public ConsumeCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(HttpServletRequest request, String key) {
//        return request.getHeader(key);
        throw new UnsupportedOperationException("not implemented yet");
    }
    
}
