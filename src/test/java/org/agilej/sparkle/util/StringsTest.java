package org.agilej.sparkle.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class StringsTest {

    @Test
    public void test_count(){
        String str = "this is a test string contains @{{ { and } with \t\n";
        assertEquals(3, Strings.count(str, "{"));
        
        assertEquals(1, Strings.count(str, "{{"));
        
        assertEquals(0, Strings.count(str, "}}"));
        
        assertEquals(1, Strings.count(str, "\t"));
        
        assertEquals(0, Strings.count(str, "hello"));
    }
    
    
    @Test
    public void test_is_digit_or_charactor(){
        assertTrue(Strings.isCharacterOrDigit("1"));
        assertTrue(Strings.isCharacterOrDigit("a"));
        assertTrue(Strings.isCharacterOrDigit("123"));
        assertTrue(Strings.isCharacterOrDigit("abc"));
        assertTrue(Strings.isCharacterOrDigit("123abc"));
        
        assertFalse(Strings.isCharacterOrDigit("123{}"));
        assertFalse(Strings.isCharacterOrDigit("-"));
        assertFalse(Strings.isCharacterOrDigit("@a"));
        assertFalse(Strings.isCharacterOrDigit(".a"));
    }
}
