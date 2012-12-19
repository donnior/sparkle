package me.donnior.sparkle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.reflections.Reflections;

import com.google.common.base.Predicates;

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
        Set<Method> methods = Reflections.getAllMethods(controller.getClass(), Predicates.and(Reflections.withModifier(Modifier.PUBLIC),Reflections.withName(actionName)));
        if(methods.isEmpty()){
            //can't find action method
            throw new RuntimeException("can't find action one controller");
        }
        Method method = methods.iterator().next();
        try {
            return method.invoke(controller, null);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

}   
