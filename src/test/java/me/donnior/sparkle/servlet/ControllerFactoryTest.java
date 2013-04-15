package me.donnior.sparkle.servlet;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import me.donnior.sparkle.internal.ControllersHolder;
import me.donnior.sparkle.servlet.ControllerFactory;

import org.junit.Before;
import org.junit.Test;


public class ControllerFactoryTest extends ControllerFactory{

    @Before
    public void setup() {
        Map<String, Class<?>> controllers = new HashMap<String, Class<?>>();
        controllers.put("testController", ControllerForControllerFactoryTest.class);
        ControllersHolder.getInstance().addControllers(controllers,true);
    }
    
    
    @Test
    public void test_get_controller_succeed(){
        Object obj = ControllerFactory.getController("testController");
        assertNotNull(obj);
    }

    @Test
    public void test_get_controller_failed(){
        Object obj = ControllerFactory.getController("testController2");
        assertNull(obj);
    }
    
}

