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
    
    //the rules for 'to' of the route: the controller can't be empty, only one '#' or zero, the action can be ommit
    private final static String toRegex = "\\w+#{0,1}\\w*";

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
        // TODO check route is correct, it should not empty and contains only one #
        // if(route == null || route.trim().equals("")){
        //     throw new RuntimeException("The controller name is empty, you can't bind route to it");
        // }
        if(route == null || !route.matches(toRegex)){
            throw new RuntimeException("route's 'to' part '" + route + "'' is illegal, it must be 'controller#action' or just 'controller'");
        }
        this.controllerName = extractController(route);
        this.actionName = extractAction(route);
    }
    
    public boolean match(String url){
        // logger.debug("matching ur {} using regex patthen {} ", url, this.matchPatten.pattern());
        boolean b = this.matchPatten.matcher(url).matches();
        logger.debug("match uri {} using pattern {} {}", new Object[]{url, this.matchPatten.pattern(), b?" success":" failed"});
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
        return route.split("#")[0];
    }

    private String extractAction(String route) {
        final String defaultAction = "index";
        String[] strs = route.split("#");
        return strs.length > 1 ? strs[1] : defaultAction;
    }

    public void setRoutePattern(String url) {
        this.routePattern = url;
    }
    
    public String getRoutePattern() {
        return routePattern;
    }

}
