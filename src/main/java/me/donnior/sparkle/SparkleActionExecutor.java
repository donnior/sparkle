package me.donnior.sparkle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SparkleActionExecutor {

    private HttpServletRequest request;
    private HttpServletResponse response;
    
    public SparkleActionExecutor(HttpServletRequest request,
            HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public Object invokeAction(Object controller, String actionName) {
        //get action method using Java reflections, resolve its arguments, and call it on the controller object.
        //TODO how to dealing 'method overloading'? means two methods with same action method name but different arguments
        return "user/index";
    }

}
