package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

import com.google.common.base.Joiner;

public abstract  class AbstractCondition {

    protected String[] params;

    public AbstractCondition(String[] params) {
        this.params = params;
    }

    public boolean match(HttpServletRequest request) {
        for(String param : params){
            String[] kv  = param.split("=");
            String expected = kv[1];
            String real = this.getConditionValueFromRequest(request, kv[0]);
            if(!expected.equals(real)){
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "\""+Joiner.on(",").join(this.params)+"\"";
    }

    public abstract String getConditionValueFromRequest(HttpServletRequest request, String key);

}