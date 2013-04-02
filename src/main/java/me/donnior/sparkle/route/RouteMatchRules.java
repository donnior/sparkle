package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

public interface RouteMatchRules {

    boolean matchPath(String path);

    ConditionMatchResult matchHeader(HttpServletRequest request);

    ConditionMatchResult matchParam(HttpServletRequest request);

    ConditionMatchResult matchConsume(HttpServletRequest request);

}