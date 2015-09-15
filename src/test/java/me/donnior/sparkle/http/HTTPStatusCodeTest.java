package me.donnior.sparkle.http;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class HTTPStatusCodeTest {

    @Test
    public void test_constant(){
        
        assertEquals(201, HTTPStatusCode.CREATED);
        
        assertEquals(404, HTTPStatusCode.NOT_FOUND);
    }
    
}
