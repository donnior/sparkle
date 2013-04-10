package me.donnior.sparkle.servlet;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.internal.ControllersHolder;

//TODO how to make the controller factory can be customized, for example let user use an
//spring container as this factory? 
public class ControllerFactory {

    public static Object getController(String controllerName) {
        Class<?> clz = ControllersHolder.getInstance().getControllerClass(controllerName);
        if(clz != null){
            return ReflectionUtil.initialize(clz);
        }
        return null;
    }

}
