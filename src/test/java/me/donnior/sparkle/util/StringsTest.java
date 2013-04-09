package me.donnior.sparkle.util;

import static org.junit.Assert.*;

import org.junit.Test;

public class StringsTest {

    @Test
    public void testCount(){
        String str = "this is a test string contains @{{ { and } with \t\n";
        assertEquals(3, Strings.count(str, "{"));
        
        assertEquals(1, Strings.count(str, "{{"));
        
        assertEquals(0, Strings.count(str, "}}"));
        
        assertEquals(1, Strings.count(str, "\t"));
        
        assertEquals(0, Strings.count(str, "hello"));
    }
    
}
