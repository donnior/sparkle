package org.agilej.sparkle.servlet;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.HashMap;
import java.util.Map;

import org.agilej.sparkle.core.method.ControllersHolder;
import org.agilej.sparkle.core.support.SimpleControllerFactory;

import org.junit.Before;
import org.junit.Test;


public class ControllerFactoryTest extends SimpleControllerFactory{

    @Before
    public void setup() {
        Map<String, Class<?>> controllers = new HashMap<String, Class<?>>();
        controllers.put("testController", ControllerForControllerFactoryTest.class);
        ControllersHolder.getInstance().registeControllers(controllers,true);
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
