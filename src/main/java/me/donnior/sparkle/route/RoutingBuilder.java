package me.donnior.sparkle.route;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import me.donnior.sparkle.HTTPMethod;

public class RoutingBuilder implements HttpScoppedRoutingBuilder, RouteMatchRule, RouteMatchRules{
    
    private HTTPMethod httpMethod;
    private String actionName;
    private String controllerName;
    private String pathPattern;
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
        this.pathPattern = url;
        this.matchPatten = Pattern.compile(checker.matcherRegexPatten());
        this.httpMethod = HTTPMethod.GET;
        logger.debug("successfully resovled route definition [source={}, pattern={}, pathVariables={}] ", new Object[]{this.pathPattern, this.matchPatten.pattern(), this.pathVariables});
    }
    
    public RoutingBuilder(Router router, List<Object> elements, Object source, String path) {
        this.httpMethod = HTTPMethod.GET;
    }
    
    public Pattern getMatchPatten() {
        return matchPatten;
    }
    
    public HTTPMethod getHttpMethod() {
        return this.httpMethod;
    }
    
    @Override
    public RoutingBuilder withGet(){
        this.httpMethod = HTTPMethod.GET ;
        return this;
    }
    
    @Override
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
    
    @Override
    public boolean matchPath(String path){
        // logger.debug("matching ur {} using regex patthen {} ", url, this.matchPatten.pattern());
        boolean b = this.matchPatten.matcher(path).matches();
//        logger.debug("match uri {} using pattern {} {}", new Object[]{path, this.matchPatten.pattern(), b?" success":" failed"});
        return b;
    }
    
    public MatchedCondition[] matchCondition(HttpServletRequest request){
        return new MatchedCondition[]{};
    }
    
    @Override
    public ConditionMatchResult matchHeader(HttpServletRequest request){
        if(hasHeaderCondition()){
            //TODO match header 
            return ConditionMatchs.EXPLICIT;
        }
        return ConditionMatchs.DEFAULT;
    }
    
    private boolean hasHeaderCondition() {
        return false;
    }

    @Override
    public ConditionMatchResult matchParam(HttpServletRequest request){
        if(hasParamCondition()){
            //TODO match param
            if(request.getParameter("a") != null){  //just for demo
                boolean matched = request.getParameter("a").equals("a");
                return matched ? ConditionMatchs.EXPLICIT : ConditionMatchs.FAILED ;
            } else {
            	return  ConditionMatchs.FAILED;
            }
        }
        return ConditionMatchs.DEFAULT;
    }
    
    private boolean hasParamCondition() {
        return false;
    }

    @Override
    public ConditionMatchResult matchConsume(HttpServletRequest request){
        if(hasConsumeCondition()){
            //TODO match consumer 
        	return ConditionMatchs.EXPLICIT;
        }
        return ConditionMatchs.DEFAULT;
    }
    
    private boolean hasConsumeCondition() {
        return false;
    }

    public String getActionName() {
        return this.actionName;
    }
    
    public String getControllerName() {
        return this.controllerName;
    }

    private String extractController(String route) {
        return route.split("#")[0];
    }

    private String extractAction(String route) {
        String[] strs = route.split("#");
        return strs.length > 1 ? strs[1] : defaultActionForMethod(this.httpMethod);
    }

    //TODO should it only set default action for GET? 
    private String defaultActionForMethod(HTTPMethod httpMethod) {
        if(HTTPMethod.DELETE == httpMethod){
            return "destroy";
        }
        if(HTTPMethod.POST == httpMethod){
            return "save";
        }
        if(HTTPMethod.PUT == httpMethod){
            return "update";
        }
        if(HTTPMethod.GET == httpMethod){
            return "index";
        }
        return null;
    }

    public void setPathPattern(String url) {
        this.pathPattern = url;
    }
    
    public String getPathPattern() {
        return pathPattern;
    }

}
