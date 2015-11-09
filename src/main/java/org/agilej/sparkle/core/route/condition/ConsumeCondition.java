package org.agilej.sparkle.core.route.condition;

import org.agilej.sparkle.WebRequest;

public class ConsumeCondition extends AbstractCondition {

    public ConsumeCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(WebRequest request, String key) {
//        return request.getHeader(key);
        throw new UnsupportedOperationException("not implemented yet");
    }
    
}
