package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

public interface RouteMatchRules {

    public abstract boolean matchPath(String path);

    public abstract ConditionMatchResult matchHeader(HttpServletRequest request);

    public abstract ConditionMatchResult matchParam(HttpServletRequest request);

    public abstract ConditionMatchResult matchConsume(HttpServletRequest request);

}