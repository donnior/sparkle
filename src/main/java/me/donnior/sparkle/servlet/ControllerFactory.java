package me.donnior.sparkle.servlet;

import me.donnior.sparkle.internal.ControllersHolder;

//TODO how to make the controller factory can be customized, for example let user use an
//spring container as this factory? 
public class ControllerFactory {

    public static Object getController(String controllerName) {
        Class<?> clz = ControllersHolder.getInstance().getControllerClass(controllerName);
        if(clz != null){
            try {
                return clz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
