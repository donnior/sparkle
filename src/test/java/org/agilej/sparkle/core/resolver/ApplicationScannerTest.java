package org.agilej.sparkle.core.resolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.agilej.sparkle.core.engine.ApplicationConfigScanner;
import org.agilej.sparkle.exception.SparkleException;

import org.junit.Test;

public class ApplicationScannerTest {

    @Test
    public void test(){
        ApplicationConfigScanner acs = new ApplicationConfigScanner();
        
        Class<?> app = acs.scan("org.agilej.sparkle.internal");
        assertNull(app);
        
        app = acs.scan("org.agilej.sparkle.data.app");
        assertNotNull(app);
        
        try{
            app = acs.scan("org.agilej.sparkle.data.exception");
            fail();
        }catch(SparkleException se){
            assertEquals("found more than one ApplicationConfig class", se.getMessage());
        }
        
    }
    
}
