package me.donnior.sparkle.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ParamResolveUtilTest {

    private ParamResolveUtil util;

    @Before
    public void setUp(){
        this.util = new ParamResolveUtil();
    }
    
    @Test
    public void testConvertToIntArray() {
        String[] values = {"1", "2", "3"};
        Object result = this.util.convertValue(values, Integer[].class);
        assertEquals(Integer[].class, result.getClass());
        assertEquals(2, ((Integer[])result)[1]);
    }

    @Test
    public void testConvertToStringArray() {
        String[] values = {"1", "2", "3"};
        Object result = this.util.convertValue(values, String[].class);
        assertEquals(String[].class, result.getClass());
        assertEquals("2", ((String[])result)[1]);
    }
    
    @Test
    public void testConvertToInt() {
        String[] values = {"1", "2", "3"};
        Object result = this.util.convertValue(values, Integer.class);
        assertEquals(Integer.class, result.getClass());
        assertEquals(1, result);
    }
    
    @Test
    public void testConvertToString() {
        String[] values = {"1", "2", "3"};
        Object result = this.util.convertValue(values, String.class);
        assertEquals(String.class, result.getClass());
        assertEquals("1", result);
    }
    
    
    //test empty but not null values
    @Test
    public void testEmptyConvertToIntArray() {
        String[] values = {};
        Object result = this.util.convertValue(values, Integer[].class);
        assertEquals(Integer[].class, result.getClass());
        assertEquals(0, ((Integer[])result).length);
    }

    @Test
    public void testEmptyConvertToStringArray() {
        String[] values = {};
        Object result = this.util.convertValue(values, String[].class);
        assertEquals(String[].class, result.getClass());
        assertEquals(0, ((String[])result).length);
    }
    
    @Test
    public void testEmptyConvertToInt() {
        String[] values = {};
        Object result = this.util.convertValue(values, Integer.class);
        assertEquals(null, result);
    }
    
    @Test
    public void testEmptyConvertToString() {
        String[] values = {};
        Object result = this.util.convertValue(values, String.class);
        assertEquals(null, result);
    }
}
