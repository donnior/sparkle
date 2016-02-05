package org.agilej.sparkle.core.resolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Map;

import org.agilej.sparkle.core.method.ControllersHolder;
import org.agilej.sparkle.exception.SparkleException;

import org.junit.Test;

import com.google.common.collect.Maps;

public class ControllerHolderTest {

    @Test
    public void test_getting(){
        ControllersHolder ch = new ControllersHolder();
        ch.registerControllers(sampleControllersMap());
        
        assertEquals(2, ch.namedControllers().size());
        assertTrue(ch.containsController("sampleOne"));
        assertTrue(ch.containsController("sampleTwo"));
        
        assertEquals(SampleOne.class, ch.getControllerClass("sampleOne"));
    }

    @Test(expected = RuntimeException.class)
    public void test_getting_with_exception(){
        ControllersHolder ch = new ControllersHolder();
        ch.registerControllers(sampleControllersMap());

        assertEquals(SampleOne.class, ch.getControllerClass("sampleThree"));
    }

    @Test
    public void test_add_with_reset(){
        ControllersHolder ch = new ControllersHolder();
        ch.registerControllers(sampleControllersMap());
        
        assertEquals(2, ch.namedControllers().size());
        
        ch.registerControllers(anotherSampleControllersMap(), true);
        
        assertEquals(1, ch.namedControllers().size());
    }
    
    @Test
    public void test_add_with_duplication(){
        ControllersHolder ch = new ControllersHolder();
        ch.registerControllers(sampleControllersMap());
        
        assertEquals(2, ch.namedControllers().size());
        
        try{
            ch.registerControllers(duplicatedControllerMap());
            fail();
        }catch(SparkleException re){
            
        }
        
        
    }
    

    private Map<String, Class<?>> sampleControllersMap() {
        Map<String, Class<?>> controllersMap = Maps.newConcurrentMap();
        
        controllersMap.put("sampleOne", SampleOne.class);
        controllersMap.put("sampleTwo", SampleTwo.class);
        
        return controllersMap;
    }
    
    private Map<String, Class<?>> duplicatedControllerMap() {
        Map<String, Class<?>> controllersMap = Maps.newConcurrentMap();
        
        controllersMap.put("sampleOne", SampleOne.class);
        
        return controllersMap;
    }    

    private Map<String, Class<?>> anotherSampleControllersMap() {
        Map<String, Class<?>> controllersMap = Maps.newConcurrentMap();
        
        controllersMap.put("sampleThree", SampleOne.class);
        
        return controllersMap;
    }
    
}

class SampleOne{
    
}

class SampleTwo{
    
}