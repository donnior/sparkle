package me.donnior.sparkel.servlet;

import me.donnior.sparkel.demo.UserController;

public class ControllerFactory {

    public static Object getController(String controllerName) {
        if("users".equals(controllerName)){
            return new UserController();
        }
        return null;
    }

}
