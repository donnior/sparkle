package me.donnior.sparkle.http;

import static org.junit.Assert.*;

import org.junit.Test;

public class HTTPStatusCodeTest {

    @Test
    public void testConstant(){
        
        assertEquals(201, HTTPStatusCode.CREATED);
        
        assertEquals(404, HTTPStatusCode.NOT_FOUND);
    }
    
}
