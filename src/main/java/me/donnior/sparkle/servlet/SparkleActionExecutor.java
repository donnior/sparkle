package me.donnior.sparkle.servlet;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.donnior.fava.Function;
import me.donnior.fava.util.FLists;
import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.internal.ActionMethodDefinition;
import me.donnior.sparkle.internal.ActionParamDefinition;
import me.donnior.sparkle.internal.DefaultParamResolversManager;
import me.donnior.sparkle.internal.ParamResolversManager;

public class SparkleActionExecutor {
    
    //TODO should refactord this params resolver, make it support multi resolvers so programmers can create their own
    // param resolver like param with class type 'Project'; so it should be List<ParamResolver>
    private ParamResolversManager paramResolver = new DefaultParamResolversManager();

    public Object invoke(ActionMethodDefinition adf, Object controller, 
            final HttpServletRequest request, final HttpServletResponse response) {
        
        Method method = adf.method();
        List<ActionParamDefinition> apds = adf.paramDefinitions();
        
        Object[] params = FLists.create(apds).collect(new Function<ActionParamDefinition, Object>() {
            public Object apply(ActionParamDefinition apd) {
                return paramResolver.resolve(apd, request);
            }
        }).toArray();
        
        return ReflectionUtil.invokeMethod(controller, method, params);
    }

}   
