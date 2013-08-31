package me.donnior.sparkle.core.resolver;

import static org.junit.Assert.*;
import me.donnior.sparkle.core.resolver.ApplicationConfigScanner;
import me.donnior.sparkle.exception.SparkleException;

import org.junit.Test;

public class ApplicationScannerTest {

    @Test
    public void test(){
        ApplicationConfigScanner acs = new ApplicationConfigScanner();
        
        Class<?> app = acs.scan("me.donnior.sparkle.internal");
        assertNull(app);
        
        app = acs.scan("me.donnior.sparkle.data.app");
        assertNotNull(app);
        
        try{
            app = acs.scan("me.donnior.sparkle.data.exception");
            fail();
        }catch(SparkleException se){
            assertEquals("found more than one ApplicationConfig class", se.getMessage());
        }
        
    }
    
}
