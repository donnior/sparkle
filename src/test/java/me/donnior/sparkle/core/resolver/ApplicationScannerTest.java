package me.donnior.sparkle.core.resolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
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
