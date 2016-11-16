package org.agilej.sparkle.core.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.agilej.sparkle.ApplicationController;
import org.agilej.sparkle.annotation.Controller;

import org.junit.Test;

public class ControllerScannerTest {

    @Test
    public void test_scan(){
        String pkg = "org.agilej.sparkle.core.handler";
        Map<String, Class<?>> controllers = new ControllerClassScanner().scanControllers(pkg);
        
        assertEquals(3, controllers.size());
        assertTrue(controllers.containsKey("sampleOne"));
        assertTrue(controllers.containsKey("SampleControllerTwo"));
        assertTrue(controllers.containsKey("sampleFour"));
        assertEquals(SampleControllerFour.class, controllers.get("sampleFour"));
    }

    @Test
    public void test_name_resolve(){
        assertEquals("sample", ControllerClassScanner.resolveName("SampleControllerTwo"));
        assertEquals("sample", ControllerClassScanner.resolveName("SampleController"));
        assertEquals("sample", ControllerClassScanner.resolveName("Sample"));
        assertEquals("u_r_l_sample", ControllerClassScanner.resolveName("URLSampleController"));

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

