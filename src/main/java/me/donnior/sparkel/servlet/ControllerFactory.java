package me.donnior.sparkel.servlet;

import me.donnior.sparkle.internal.ControllersHolder;

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
