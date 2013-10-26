package me.donnior.eset;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PathTest {

    @Test
    public void testParsedParams(){
        Path path = new Path("user[address][0]");
        assertTrue("user[address[0".equals(path.name()));
        
    }
    
    
 
    
}
