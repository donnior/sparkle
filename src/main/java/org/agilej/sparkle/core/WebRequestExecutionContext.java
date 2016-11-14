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
    private Object viewResult;
    private ActionMethod actionMethod;

    public WebRequestExecutionContext(WebRequest webRequest){
        this(webRequest, null);
    }

    public WebRequestExecutionContext(WebRequest webRequest, InterceptorExecutionChain ic) {
        this.webRequest = webRequest;
        this.interceptorExecutionChain = ic;
    }

    public void startTimer(String timerName){
        this.stopwatch.start();
    }

    public void endTimer(String timerName){
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

    public Object getViewResult() {
        return viewResult;
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
}
