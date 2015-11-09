package org.agilej.sparkle.core.route.condition;

import org.agilej.sparkle.WebRequest;


public class ParamCondition extends AbstractCondition {

    public ParamCondition(String[] params) {
        super(params);
    }

    public String getConditionValueFromRequest(WebRequest request, String key) {
        return request.getParameter(key);
    }
    
}
