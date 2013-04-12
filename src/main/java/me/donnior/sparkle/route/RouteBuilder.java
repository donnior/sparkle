package me.donnior.sparkle.route;

import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.condition.AbstractCondition;
import me.donnior.sparkle.condition.ConsumeCondition;
import me.donnior.sparkle.condition.HeaderCondition;
import me.donnior.sparkle.condition.ParamCondition;
import me.donnior.sparkle.exception.SparkleException;
import me.donnior.sparkle.rest.RestStandard;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteBuilder implements HttpScoppedRoutingBuilder, RouteMatchRules{
    
    private HTTPMethod httpMethod;
    private String actionName;
    private String controllerName;
    private String pathPattern;
    private List<String> pathVariables;
    private Pattern matchPatten;
    private AbstractCondition paramCondition;
    private AbstractCondition headerCondition;
    private AbstractCondition consumeCondition;
    
    //the rules for 'to' of the route: the controller can't be empty, only one '#' or zero, the action can be ommit
    private final static String toRegex = "\\w+#{0,1}\\w*";

    private final static Logger logger = LoggerFactory.getLogger(RouteBuilder.class);

    public RouteBuilder(String url){
        RouteChecker checker = new RouteChecker(url);
        this.pathVariables = checker.pathVariables();
        this.pathPattern = url;
        this.matchPatten = Pattern.compile(checker.matcherRegexPatten());
        this.httpMethod = HTTPMethod.GET;
        logger.debug("successfully resovled route definition [source={}, pattern={}, pathVariables={}] ", new Object[]{this.pathPattern, this.matchPatten.pattern(), this.pathVariables});
    }
    
    public Pattern getMatchPatten() {
        return matchPatten;
    }
    
    public HTTPMethod getHttpMethod() {
        return this.httpMethod;
    }
    
    @Override
    public RouteBuilder withGet(){
        this.httpMethod = HTTPMethod.GET ;
        return this;
    }
    
    @Override
    public RouteBuilder withPost(){
        this.httpMethod = HTTPMethod.POST ;
        return this;
    }
    
    @Override
    public ConditionalRoutingBuilder matchParams(String... params) {
        //TODO how to deal this method called many times
        if(params != null){
            this.paramCondition = new ParamCondition(params);
        }
        return this;
    }
    
    @Override
    public ConditionalRoutingBuilder matchHeaders(String... params) {
        if(params != null){
            this.headerCondition = new HeaderCondition(params);
        }
        return this;
    }
    
    @Override
    public ConditionalRoutingBuilder matchConsumes(String... params) {
        if(params != null){
            this.consumeCondition = new ConsumeCondition(params);
        }
        return this;
    }
    
    
    @Override
    public void to(String route){
        // TODO check route is correct, it should not empty and contains only one #
        if(route == null || !route.matches(toRegex)){
            throw new SparkleException("route's 'to' part '" + route + "' is illegal, it must be 'controller#action' or just 'controller'");
        }
        this.controllerName = extractController(route);
        this.actionName = extractAction(route);
    }
    
    @Override
    public boolean matchPath(String path){
        boolean b = this.matchPatten.matcher(path).matches();
        logger.debug("match uri {} using pattern {} {}", new Object[]{path, this.matchPatten.pattern(), b?" success":" failed"});
        return b;
    }
    
//    public MatchedCondition[] matchCondition(HttpServletRequest request){
//        return new MatchedCondition[]{};
//    }
//    
    @Override
    public ConditionMatchResult matchHeader(HttpServletRequest request){
        if(hasHeaderCondition()){
            return this.headerCondition.match(request) ? ConditionMatchs.EXPLICIT_SUCCEED : ConditionMatchs.FAILED;
        }
        return ConditionMatchs.DEFAULT_SUCCEED;
    }
    
    private boolean hasHeaderCondition() {
        return this.headerCondition != null;
    }

    @Override
    public ConditionMatchResult matchParam(HttpServletRequest request){
        if(hasParamCondition()){
            return this.paramCondition.match(request) ? ConditionMatchs.EXPLICIT_SUCCEED : ConditionMatchs.FAILED;
        }
        return ConditionMatchs.DEFAULT_SUCCEED;
    }
    
    private boolean hasParamCondition() {
        return this.paramCondition != null;
    }

    @Override
    public ConditionMatchResult matchConsume(HttpServletRequest request){
        if(hasConsumeCondition()){
            return this.consumeCondition.match(request) ? ConditionMatchs.EXPLICIT_SUCCEED : ConditionMatchs.FAILED;
        }
        return ConditionMatchs.DEFAULT_SUCCEED;
    }
    
    private boolean hasConsumeCondition() {
        return this.consumeCondition != null;
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
        return strs.length > 1 ? strs[1] : RestStandard.defaultActionMethodNameForHttpMethod(this.httpMethod);
    }

    //TODO should it only set default action for GET? 


    public void setPathPattern(String url) {
        this.pathPattern = url;
    }
    
    public String getPathPattern() {
        return pathPattern;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("RouteBuilder:[");
        sb.append("path=>"+this.pathPattern);
        sb.append(","+"method=>"+this.httpMethod);
        if(this.hasParamCondition()){
            sb.append(","+"params=>"+this.paramCondition.toString());
        }
        sb.append("]");
        return sb.toString();
    }

    public boolean matchMethod(HTTPMethod method) {
        return this.getHttpMethod().equals(method);
    }

}
