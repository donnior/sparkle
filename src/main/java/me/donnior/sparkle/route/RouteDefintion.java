package me.donnior.sparkle.route;

import me.donnior.sparkle.HTTPMethod;

public interface RouteDefintion {

    public String getControllerName();
    
    public String getActionName();
    
    public HTTPMethod getHttpMethod();

}
