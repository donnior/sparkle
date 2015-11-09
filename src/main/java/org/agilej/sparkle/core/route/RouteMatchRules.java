package org.agilej.sparkle.core.route;

import org.agilej.sparkle.WebRequest;

public interface RouteMatchRules {

    boolean matchPath(String path);

    ConditionMatchResult matchHeader(WebRequest request);

    ConditionMatchResult matchParam(WebRequest request);

    ConditionMatchResult matchConsume(WebRequest request);

}