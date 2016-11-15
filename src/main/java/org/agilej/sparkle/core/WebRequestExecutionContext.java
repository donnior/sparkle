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
    private RouteInfo route;
    private Object controller;
    private Object actionResult;
    private ActionMethod actionMethod;
    private Object[] arguments;

    private Stopwatch stopwatch = Stopwatch.createUnstarted();

    public WebRequestExecutionContext(WebRequest webRequest){
        this.webRequest = webRequest;
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

    public void setActionResult(Object actionResult) {
        this.actionResult = actionResult;
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

    public Object getActionResult() {
        return this.actionResult;
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
