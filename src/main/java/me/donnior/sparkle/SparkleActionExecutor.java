package me.donnior.sparkle;

import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.internal.ActionMethodDefinition;
import me.donnior.sparkle.internal.ActionParamDefinition;
import me.donnior.sparkle.internal.DefaultParamResolversManager;
import me.donnior.sparkle.internal.ParamResolversManager;

public class SparkleActionExecutor {
    
    //TODO should refactord this params resolver, make it support multi resolvers so programmers can create theire own
    // param resolver like param with class type 'Project'; so it should be List<ParamResolver>
    private ParamResolversManager paramResolver = new DefaultParamResolversManager();

    public Object invoke(ActionMethodDefinition adf, Object controller, HttpServletRequest request) {
        Method method = adf.method();
        List<ActionParamDefinition> apds = adf.paramDefinitions();
        
        Object[] params = new Object[apds.size()];
        for(int i = 0; i< apds.size(); i++){
            ActionParamDefinition apd = apds.get(i);
            params[i] = this.paramResolver.resolve(apd, request);
        }
        return ReflectionUtil.invokeMethod(controller, method, params);
    }

}   
