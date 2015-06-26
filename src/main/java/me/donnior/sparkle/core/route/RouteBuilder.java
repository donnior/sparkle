package me.donnior.sparkle.core.route;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.route.condition.AbstractCondition;
import me.donnior.sparkle.core.route.condition.ConsumeCondition;
import me.donnior.sparkle.core.route.condition.HeaderCondition;
import me.donnior.sparkle.core.route.condition.ParamCondition;
import me.donnior.sparkle.exception.SparkleException;
import me.donnior.sparkle.route.ConditionalRoutingBuilder;
import me.donnior.sparkle.route.HttpScoppedRoutingBuilder;

import me.donnior.sparkle.route.LinkedRoutingBuilder;
import org.agilej.jsonty.JSONModel;
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
    private String to;
    
    //the rules for 'to' of the route: the controller can't be empty, only one '#' or zero, the action can be ommit
    private final static String toRegex = "\\w+#{0,1}\\w*";

    private final static Logger logger = LoggerFactory.getLogger(RouteBuilder.class);
    private Function<WebRequest, JSONModel> function;
    private boolean isFunctionMode;

    public RouteBuilder(String url){
        RouteChecker checker = new RouteChecker(url);
        this.pathVariables = checker.pathVariables();
        this.pathPattern = url;
        this.matchPatten = Pattern.compile(checker.matcherRegexPatten());
        this.httpMethod = HTTPMethod.GET;
        logger.debug("Create route definition [source={}, pattern={}, pathVariables={}] ",
                new Object[]{this.pathPattern, this.matchPatten.pattern(), this.pathVariables});
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
    public LinkedRoutingBuilder withPut() {
        this.httpMethod = HTTPMethod.PUT;
        return this;
    }

    @Override
    public LinkedRoutingBuilder withDelete() {
        this.httpMethod = HTTPMethod.DELETE;
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
        this.to = route;
        // TODO check route is correct, it should not empty and contains only one #
        if(route == null || !route.matches(toRegex)){
            throw new SparkleException("route's 'to' part '" + route + "' is illegal, it must be 'controller#action' or just 'controller'");
        }
        this.controllerName = extractController(route);
        this.actionName = extractAction(route);
    }

    @Override
    public void to(Function<WebRequest, JSONModel> function) {
        this.function = function;
        this.isFunctionMode = true;
    }

    public boolean isFunctionRoute(){
        return this.isFunctionMode;
    }

    public Function<WebRequest, JSONModel> getRouteFunction(){
        return this.function;
    }

    @Override
    public boolean matchPath(String path){
        //TODO how about introducing a pattern-path-match cache? reduce the cost creating a matcher every time
        if(path.endsWith("/") && path.length()>1){
            path = path.substring(0, path.length()-1);
        }
        //IDEA 也许我们可以直接使用path和pathPattern进行字符串的equals比较，但是仅限于当前路由定义中不包含正则部分
        //比如我们定义了一个不需要正则的RouteBuilder("/users/home")或者("/"),则可直接对请求的path进行equals判断，加快速度 
        boolean b = this.matchPatten.matcher(path).matches();
        logger.debug("match uri {} using pattern {} {}", new Object[]{path, this.matchPatten.pattern(), b?" success":" failed"});
        return b;
    }
    
    //TODO 重构此方法，放至合适的地方
    public List<String> extractPathVariableValues(String path){
        List<String> variables = new ArrayList<String>();
        Pattern pattern = this.matchPatten;
        Matcher matcher = pattern.matcher(path);

        if (matcher.matches()) {
            int count = matcher.groupCount();
            for (int index = 1; index <= count; index++) {
                String group = matcher.group(index);
                variables.add(group);
            }
        }
        
        return variables;
    }
    
    @Override
    public ConditionMatchResult matchHeader(WebRequest request){
        if(hasHeaderCondition()){
            return this.headerCondition.match(request) ? ConditionMatchs.EXPLICIT_SUCCEED : ConditionMatchs.FAILED;
        }
        return ConditionMatchs.DEFAULT_SUCCEED;
    }
    
    private boolean hasHeaderCondition() {
        return this.headerCondition != null;
    }

    @Override
    public ConditionMatchResult matchParam(WebRequest request){
        if(hasParamCondition()){
            return this.paramCondition.match(request) ? ConditionMatchs.EXPLICIT_SUCCEED : ConditionMatchs.FAILED;
        }
        return ConditionMatchs.DEFAULT_SUCCEED;
    }
    
    private boolean hasParamCondition() {
        return this.paramCondition != null;
    }

    @Override
    public ConditionMatchResult matchConsume(WebRequest request){
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
    
    public String getPathPattern() {
        return pathPattern;
    }
    
    public List<String> getPathVariables() {
        return pathVariables;
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("path => "+this.pathPattern);
        sb.append(" , "+"method => "+this.httpMethod);
        if(this.hasParamCondition()){
            sb.append(" , "+"params=>"+this.paramCondition.toString());
        }
        sb.append(" , to => " + this.to);
        sb.append(" }");
        return sb.toString();
    }

    public boolean matchMethod(HTTPMethod method) {
        return this.getHttpMethod().equals(method);
    }

}
