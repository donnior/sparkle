package me.donnior.sparkle.route;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.HTTPMethod;

public class RouteBuilderMatcher {

    private HttpServletRequest request;
    private RoutingBuilder builder;

    private List<MatchedCondition> explicitMatchedCondition = new ArrayList<MatchedCondition>();

    public RouteBuilderMatcher(RoutingBuilder builder,
            HttpServletRequest request) {
        this.builder = builder;
        this.request = request;
    }

    public boolean match() {

        final String path = extractPath(request);
        final HTTPMethod method = extractMethod(request);

        boolean pathAndMethodMatched = builder.matchPath(path) && (builder.getHttpMethod() == method);
        if (!pathAndMethodMatched) {
            return false;
        } else {
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

            ConditionMatchResult consumeMatchResult = builder
                    .matchConsume(request);
            if (!consumeMatchResult.succeed()) {
                return false;
            }
            if (consumeMatchResult.isExplicitMatch()) {
                this.explicitMatchedCondition.add(consumeMatchResult);
            }
            return true;
        }
    }

    public MatchedCondition[] matchedExplicitConditions() {
        return this.explicitMatchedCondition.toArray(new MatchedCondition[] {});
    }

    public RoutingBuilder getBuilder() {
        return builder;
    }

    private HTTPMethod extractMethod(HttpServletRequest request) {
        if ("get".equals(request.getMethod().toLowerCase())) {
            return HTTPMethod.GET;
        }
        if ("post".equals(request.getMethod().toLowerCase())) {
            return HTTPMethod.POST;
        }
        return HTTPMethod.GET;
    }

    private String extractPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        String servletPath = request.getServletPath();
        String pathInfo = request.getPathInfo();
        String cAndActionString = pathInfo;

        if (pathInfo == null) {
            //wild servlet mapping like "/" or "*.do"
            cAndActionString = request.getServletPath();
        } else {
            //normal mapping like "/cms/*"
        }

        return cAndActionString;
    }

}
