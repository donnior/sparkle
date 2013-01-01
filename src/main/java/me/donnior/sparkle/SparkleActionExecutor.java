package me.donnior.sparkle;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.sparkle.internal.ActionMethodDefinition;
import me.donnior.sparkle.internal.ActionParamDefinition;
import me.donnior.sparkle.internal.DefaultParamResolver;
import me.donnior.sparkle.internal.ParamResolver;

public class SparkleActionExecutor {

    private HttpServletRequest request;
    private HttpServletResponse response;
    
    //TODO should refactord this params resolver, make it support multi resolvers so programmers can create theire own
    // param resolver like param with class type 'Project'; so it should be List<ParamResolver>
    private ParamResolver paramResolver = new DefaultParamResolver();

    public Object invoke(ActionMethodDefinition adf, Object controller, HttpServletRequest request) {
        Method method = adf.method();
        List<ActionParamDefinition> apds = adf.paramDefinitions();
        
        System.out.println("action " + adf.actionName() + " is expecting " + apds.size() + " params");
        Object[] params = new Object[apds.size()];
        for(int i = 0; i< apds.size(); i++){
            ActionParamDefinition apd = apds.get(i);
            
            params[i] = this.paramResolver.resolve(apd, request);
        }
        try {
            System.out.println("invoke action with params " + params.length);
            return method.invoke(controller, params);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}   
