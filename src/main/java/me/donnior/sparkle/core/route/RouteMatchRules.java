package me.donnior.sparkle.core.route;

import me.donnior.sparkle.WebRequest;

public interface RouteMatchRules {

    boolean matchPath(String path);

    ConditionMatchResult matchHeader(WebRequest request);

    ConditionMatchResult matchParam(WebRequest request);

    ConditionMatchResult matchConsume(WebRequest request);

}