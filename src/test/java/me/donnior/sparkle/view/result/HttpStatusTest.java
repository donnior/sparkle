package me.donnior.sparkle.view.result;

import static org.junit.Assert.*;

import org.junit.Test;

public class HttpStatusTest {

    @Test
    public void testHttpStatusResult(){
        HttpStatus s1 = new HttpStatus(100, "unknow result");
        assertEquals(100, s1.getCode());
        assertEquals("unknow result", s1.getMessage());
        
        HttpStatus s2 = new HttpStatus(404, "not found");
        assertEquals(404, s2.getCode());
        assertEquals("not found", s2.getMessage());
    }
    
}
