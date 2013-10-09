package me.donnior.sparkle.core.route;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.google.common.collect.Lists;

public class RouteBuilderMatcher {

    private HttpServletRequest request;
    private RouteBuilder builder;

    private List<MatchedCondition> explicitMatchedCondition = Lists.newArrayList();

    public RouteBuilderMatcher(RouteBuilder builder, HttpServletRequest request) {
        this.builder = builder;
        this.request = request;
    }

    public boolean match() {

        boolean pathAndMethodMatched = isMethodMatched() && isPathMatched();
        if (!pathAndMethodMatched) {
            return false;
        } 
        ConditionMatchResult paramMatchResult = builder.matchParam(request);
        if (!paramMatchResult.succeed()) {
            return false;
        }
        if (paramMatchResult.isExplicitMatch()) {
            this.explicitMatchedCondition.add(paramMatchResult);
        }
        ConditionMatchResult headerMatchResult = builder.matchHeader(request);
        if (!headerMatchResult.succeed()) {
            return false;
        }
        if (headerMatchResult.isExplicitMatch()) {
            this.explicitMatchedCondition.add(headerMatchResult);
        }

//        ConditionMatchResult consumeMatchResult = builder.matchConsume(request);
//        if (!consumeMatchResult.succeed()) {
//            return false;
//        }
//        if (consumeMatchResult.isExplicitMatch()) {
//            this.explicitMatchedCondition.add(consumeMatchResult);
//        }
        return true;
    }

    private boolean isMethodMatched() {
        return builder.matchMethod(RouteMethodDetector.detectMethod(request));
    }

    private boolean isPathMatched() {
        return builder.matchPath(RoutePathDetector.extractPath(request));
    }
    
    

    public MatchedCondition[] matchedExplicitConditions() {
        return this.explicitMatchedCondition.toArray(new MatchedCondition[] {});
    }

    public RouteBuilder getBuilder() {
        return builder;
    }

}
