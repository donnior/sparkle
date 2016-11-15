package org.agilej.sparkle.core;

import com.google.common.base.Stopwatch;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethod;
import org.agilej.sparkle.core.execute.InterceptorExecutionChain;
import org.agilej.sparkle.core.route.RouteInfo;

import java.util.concurrent.TimeUnit;

public class WebRequestExecutionContext {

    private final WebRequest webRequest;
    private InterceptorExecutionChain interceptorExecutionChain;
    private Stopwatch stopwatch = Stopwatch.createUnstarted();
    private RouteInfo route;
    private Object controller;
    private Object controllerActionResult;
    private ActionMethod actionMethod;
    private Object[] arguments;

    public WebRequestExecutionContext(WebRequest webRequest){
        this(webRequest, null);
    }

    public WebRequestExecutionContext(WebRequest webRequest, InterceptorExecutionChain ic) {
        this.webRequest = webRequest;
        this.interceptorExecutionChain = ic;
    }

    public void startTimer(){
        this.stopwatch.start();
    }

    public void endTimer(){
        this.stopwatch.stop();
        long time = stopwatch.elapsed(TimeUnit.MILLISECONDS);
        stopwatch.reset();
    }

    public Stopwatch stopwatch(){
        return this.stopwatch;
    }

    public WebRequest webRequest() {
        return this.webRequest;
    }

    public InterceptorExecutionChain interceptorExecutionChain(){
        return this.interceptorExecutionChain;
    }

    public void setRoute(RouteInfo route) {
        this.route = route;
    }

    public RouteInfo getRoute() {
        return route;
    }

    public void setController(Object controller) {
        this.controller = controller;
    }

    public void setControllerActionResult(Object controllerActionResult) {
        this.controllerActionResult = controllerActionResult;
    }

    public Object getController() {
        return controller;
    }

    public ActionMethod getActionMethod() {
        return actionMethod;
    }

    public void setActionMethod(ActionMethod actionMethod) {
        this.actionMethod = actionMethod;
    }

    public Object getControllerActionResult() {
        return controllerActionResult;
    }

    public void setInterceptorExecutionChain(InterceptorExecutionChain interceptorExecutionChain) {
        this.interceptorExecutionChain = interceptorExecutionChain;
    }

    public void setArguments(Object[] arguments) {
        this.arguments = arguments;
    }

    public Object[] getArguments() {
        return arguments;
    }
}
