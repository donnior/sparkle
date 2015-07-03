package me.donnior.sparkle.core.route;

import java.util.List;

import me.donnior.sparkle.WebRequest;

import com.google.common.collect.Lists;

/**
 * route matcher for one request and one route builder
 */
public class RouteBuilderMatcher {

    private WebRequest request;
    private RouteBuilder builder;

    private List<MatchedCondition> explicitMatchedCondition = Lists.newArrayList();

    public RouteBuilderMatcher(RouteBuilder builder, WebRequest webRequest) {
        this.builder = builder;
        this.request = webRequest;
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
        return builder.matchPath(request.getPath());
    }

    public MatchedCondition[] matchedExplicitConditions() {
        return this.explicitMatchedCondition.toArray(new MatchedCondition[] {});
    }

    public RouteBuilder getBuilder() {
        return builder;
    }

}
