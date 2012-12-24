package me.donnior.sparkle.route;

import java.util.List;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.donnior.sparkle.HTTPMethod;

public class RoutingBuilder implements HttpScoppedRoutingBuilder, RouteDefintion{
    
    private HTTPMethod httpMethod;
    private String actionName;
    private String controllerName;
    private String routePattern;
    private List<String> pathVariables;
    private Pattern matchPatten;
    
    private final static Logger logger = LoggerFactory.getLogger(RoutingBuilder.class);

    public RoutingBuilder() {
        
    }

    public RoutingBuilder(String url){
        RouteChecker checker = new RouteChecker(url);
        this.pathVariables = checker.pathVariables();
        this.routePattern = url;
        this.matchPatten = Pattern.compile(checker.matcherRegexPatten());
        logger.debug("successfully resovled route definition [source={}, pattern={}, pathVariables={}] ", new Object[]{this.routePattern, this.matchPatten.pattern(), this.pathVariables});
    }
    
    public RoutingBuilder(Router router, List<Object> elements, Object source, String path) {
        this.httpMethod = HTTPMethod.GET;
    }
    
    public Pattern getMatchPatten() {
        return matchPatten;
    }
    
    @Override
    public RoutingBuilder withGet(){
        this.httpMethod = HTTPMethod.GET ;
        return this;
    }
    
//    @Override
    public RoutingBuilder withPost(){
        this.httpMethod = HTTPMethod.POST ;
        return this;
    }
    
    @Override
    public void to(String route){
        this.controllerName = extractController(route);
        this.actionName = extractAction(route);
//        return this;
    }
    
    public boolean match(String url){
//        return this.getRoutePattern().equals(url);
        logger.debug("matching url {} using regex patthen {} ", url, this.matchPatten.pattern());
        boolean b = this.matchPatten.matcher(url).matches();
        
        logger.debug("matched {}", b);
        return b;
    }
    
    
    @Override
    public String getActionName() {
        return this.actionName;
    }
    
    @Override
    public String getControllerName() {
        return this.controllerName;
    }

    private String extractController(String route) {
        if(route == null || route.trim().equals("")){
            throw new RuntimeException("The controller name is empty, you can't bind route to it");
        }
        return route.split("#")[0];
    }

    private String extractAction(String route) {
        // TODO deal route without action, means to default action 'index'
        return route.split("#")[1];
    }

    public void setRoutePattern(String url) {
        this.routePattern = url;
    }
    
    public String getRoutePattern() {
        return routePattern;
    }

}
