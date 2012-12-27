package me.donnior.sparkle.route;

import javax.servlet.http.HttpServletRequest;

public interface RouteMatchRules {

    public abstract boolean matchPath(String path);

    public abstract boolean matchHeader(HttpServletRequest request);

    public abstract boolean matchParam(HttpServletRequest request);

    public abstract boolean matchConsume(HttpServletRequest request);

}