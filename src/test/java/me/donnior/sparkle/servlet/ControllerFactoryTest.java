package me.donnior.sparkle.servlet;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import me.donnior.sparkle.internal.ControllersHolder;
import me.donnior.sparkle.servlet.SimpleControllerFactory;

import org.junit.Before;
import org.junit.Test;


public class ControllerFactoryTest extends SimpleControllerFactory{

    @Before
    public void setup() {
        Map<String, Class<?>> controllers = new HashMap<String, Class<?>>();
        controllers.put("testController", ControllerForControllerFactoryTest.class);
        ControllersHolder.getInstance().addControllers(controllers,true);
    }
    
    
    @Test
    public void test_get_controller_succeed(){
        Object obj = new SimpleControllerFactory().get("testController", ControllerForControllerFactoryTest.class);
        assertNotNull(obj);
    }

    @Test
    public void test_get_controller_failed(){
        Object obj = new SimpleControllerFactory().get("testController", null);
        assertNull(obj);
    }
    
}

