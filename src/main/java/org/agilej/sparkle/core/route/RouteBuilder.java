package org.agilej.sparkle.core.route;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import java.util.regex.Pattern;

import org.agilej.sparkle.HTTPMethod;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.route.condition.AbstractCondition;
import org.agilej.sparkle.core.route.condition.ConsumeCondition;
import org.agilej.sparkle.core.route.condition.HeaderCondition;
import org.agilej.sparkle.core.route.condition.ParamCondition;
import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.route.ConditionalRoutingBuilder;
import org.agilej.sparkle.route.HttpScopedRoutingBuilder;
import org.agilej.sparkle.route.LinkedRoutingBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteBuilder implements HttpScopedRoutingBuilder, RouteMatchRules, RouteInfo{

    private final RoutePathDetector pathDetector;

    private HTTPMethod httpMethod;
    private String actionName;
    private String controllerName;
    private String pathTemplate;

    private AbstractCondition paramCondition;
    private AbstractCondition headerCondition;
    private AbstractCondition consumeCondition;

    private Function<WebRequest, Object> function;
    private String to;
    
    //the rules for 'to' of the route: the controller can't be empty, only one '#' or zero, the handler can be ommit
    private final static String toRegex = "\\w+#{0,1}\\w*";

    private final static Logger logger = LoggerFactory.getLogger(RouteBuilder.class);

    public RouteBuilder(String url){
        this.pathTemplate = url;
        this.httpMethod = HTTPMethod.GET;
        this.pathDetector = new RoutePathDetector(url);
    }
    
    public Pattern getMatchPatten() {
        return this.pathDetector.matcherPattern();
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
        // TODO check route is correct, it should not empty and contains only one #
        if(route == null || !route.matches(toRegex)){
            SparkleException.raise("Route's 'to' part ['%s'] is illegal, it must be 'controller#handler' or just 'controller'", route);
        }

        this.to = route;
        String[] split = route.split("#");
        this.controllerName = split[0];
        this.actionName = split.length > 1 ? split[1]
                                           : RestStandard.defaultActionMethodNameForHttpMethod(this.httpMethod);
    }

    @Override
    public void to(Function<WebRequest, Object> function) {
        SparkleException.throwIf(function == null, "route function can't be null");
        this.function = function;
    }

    @Override
    public boolean isFunctionRoute(){
        return this.function != null;
    }

    @Override
    public Function<WebRequest, Object> getRouteFunction(){
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
        boolean b = this.pathDetector.matches(path);
        logger.debug("match uri {} using pattern {} {}", new Object[]{path, this.pathDetector.matcherPattern(), b ? " success" : " failed"});
        return b;
    }

    public List<String> extractPathVariableValues(String path){
        return this.pathDetector.extractPathVariableValues(path);
    }
    
    @Override
    public ConditionMatchResult matchHeader(WebRequest request){
        if(hasHeaderCondition()){
            return this.headerCondition.match(request) ? ConditionMatchs.EXPLICIT_SUCCEED : ConditionMatchs.FAILED;
        }
        return ConditionMatchs.DEFAULT_SUCCEED;
    }
    
    public boolean hasHeaderCondition() {
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

    @Override
    public String getActionName() {
        return this.actionName;
    }

    @Override
    public String getControllerName() {
        return this.controllerName;
    }

    public String getPathTemplate() {
        return pathTemplate;
    }

    public boolean matchMethod(HTTPMethod method) {
        return this.getHttpMethod().equals(method);
    }

    @Override
    public List<String> getPathVariableNames() {
        return this.pathDetector.pathVariableNames();
    }

    @Override
    public Map<String, String > pathVariables(String path) {
        return  this.pathDetector.pathVariableNames(path);
    }


    @Override
    public String toString() {
//        return MoreObjects.toStringHelper(this)
//                .omitNullValues()
//                .add("path", this.pathTemplate)
//                .add("method", this.httpMethod)
//                .add("params", this.paramCondition)
//                .add("header", this.headerCondition)
//                .add("consumes", this.consumeCondition)
//                .add("to", this.isFunctionRoute() ? "request -> {...}" : this.to)
//                .toString();


        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        sb.append("path => "+this.pathTemplate);
        sb.append(" , "+"method => "+this.httpMethod);
        if(this.hasParamCondition()){
            sb.append(" , "+"params => "+this.paramCondition.toString());
        }
        if (this.hasHeaderCondition()){
            sb.append(" , "+"header => "+this.headerCondition.toString());
        }
        if (this.hasConsumeCondition()){
            sb.append(" , "+"consumes => "+this.consumeCondition.toString());
        }
        sb.append(" , to => ");
        if (this.isFunctionRoute()){
            sb.append("request -> {...}");
        } else {
            sb.append(this.to);
        }
        sb.append(" }");
        return sb.toString();
    }



}
