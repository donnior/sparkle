package me.donnior.sparkle.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import me.donnior.sparkle.annotation.Controller;
import me.donnior.sparkle.controller.ApplicationController;

import org.junit.Test;

public class ControllerScannerTest {

    @Test
    public void testScan(){
        String pkg = "me.donnior.sparkle.internal";
        Map<String, Class<?>> controllers = new ControllerScanner().scanControllers(pkg);
        
        assertEquals(3, controllers.size());
        assertTrue(controllers.containsKey("sampleOne"));
        assertTrue(controllers.containsKey("SampleControllerTwo"));
        assertTrue(controllers.containsKey("sampleFour"));
        assertEquals(SampleControllerFour.class, controllers.get("sampleFour"));
    }
}

@Controller("sampleOne")
class SampleControllerOne{
    
}

class SampleControllerTwo extends ApplicationController{
    
}

@Controller("sampleOne")
class SampleControllerThree{
    
}

@Controller("sampleFour")
class SampleControllerFour extends ApplicationController{
    
}

