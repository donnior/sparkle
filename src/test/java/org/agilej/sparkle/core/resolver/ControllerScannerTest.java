package org.agilej.sparkle.core.resolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.agilej.sparkle.ApplicationController;
import org.agilej.sparkle.annotation.Controller;

import org.agilej.sparkle.core.method.ControllerScanner;
import org.junit.Test;

public class ControllerScannerTest {

    @Test
    public void test_scan(){
        String pkg = "org.agilej.sparkle.core.resolver";
        Map<String, Class<?>> controllers = new ControllerScanner().scanControllers(pkg);
        
        assertEquals(3, controllers.size());
        assertTrue(controllers.containsKey("sampleOne"));
        assertTrue(controllers.containsKey("SampleControllerTwo"));
        assertTrue(controllers.containsKey("sampleFour"));
        assertEquals(SampleControllerFour.class, controllers.get("sampleFour"));
    }

    @Test
    public void test_name_resolve(){
        assertEquals("sample", ControllerScanner.resolveName("SampleControllerTwo"));
        assertEquals("sample", ControllerScanner.resolveName("SampleController"));
        assertEquals("sample", ControllerScanner.resolveName("Sample"));
        assertEquals("u_r_l_sample", ControllerScanner.resolveName("URLSampleController"));

    }
}

@Controller("sampleOne")
class SampleControllerOne{
    
}

class SampleControllerTwo extends ApplicationController {
    
}

@Controller("sampleOne")
class SampleControllerThree{
    
}

@Controller("sampleFour")
class SampleControllerFour extends ApplicationController{
    
}

