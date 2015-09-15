package me.donnior.sparkle.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

public class ParamResolveUtilTest {

    private ParamResolveUtil util;

    @Before
    public void setUp(){
        this.util = new ParamResolveUtil();
    }
    
    @Test(expected=RuntimeException.class)
    public void test_convert_null(){
        this.util.convertValue(null, String.class);
    }
    
    @Test
    public void test_convert_to_int_array() {
        String[] values = {"1", "2", "3"};
        Object result = this.util.convertValue(values, Integer[].class);
        assertEquals(Integer[].class, result.getClass());
        assertEquals(2, ((Integer[])result)[1]);
    }

    @Test
    public void test_convert_to_string_array() {
        String[] values = {"1", "2", "3"};
        Object result = this.util.convertValue(values, String[].class);
        assertEquals(String[].class, result.getClass());
        assertEquals("2", ((String[])result)[1]);
    }
 
    @Test
    public void test_convert_to_int() {
        String[] values = {"1", "2", "3"};
        
        Object result = this.util.convertValue(values, Integer.class);
        assertEquals(Integer.class, result.getClass());
        assertEquals(1, result);
        
        result = this.util.convertValue(values, int.class);
        assertEquals(Integer.class, result.getClass());
        assertEquals(1, result);
    }
    
    @Test
    public void test_convert_to_float() {
        String[] values = {"1", "2", "3"};
        
        Object result = this.util.convertValue(values, Float.class);
        assertEquals(Float.class, result.getClass());
        assertEquals(0, Float.compare(1.0f, (Float)result));
        
        result = this.util.convertValue(values, float.class);
        assertEquals(Float.class, result.getClass());
        assertEquals(0, Float.compare(1.0f, (Float)result));
    }
 
    @Test
    public void test_convert_to_double() {
        String[] values = {"1", "2", "3"};
        
        Object result = this.util.convertValue(values, Double.class);
        assertEquals(Double.class, result.getClass());
        assertEquals(0, Double.compare(1.0f, (Double)result));
        
        result = this.util.convertValue(values, double.class);
        assertEquals(Double.class, result.getClass());
        assertEquals(0, Double.compare(1.0f, (Double)result));
    }
        
    @Test
    public void test_convert_to_string() {
        String[] values = {"1", "2", "3"};
        Object result = this.util.convertValue(values, String.class);
        assertEquals(String.class, result.getClass());
        assertEquals("1", result);
    }

    @Test(expected=RuntimeException.class)
    public void test_convert_to_not_supported_type() {
        String[] values = {"1", "2", "3"};
        this.util.convertValue(values, ParamResolveUtilTest.class);
        fail();
    }
    
    //test empty but not null values
    @Test
    public void test_empty_convert_to_int_array() {
        String[] values = {};
        Object result = this.util.convertValue(values, Integer[].class);
        assertEquals(Integer[].class, result.getClass());
        assertEquals(0, ((Integer[])result).length);
    }

    @Test
    public void test_empty_convert_to_string_array() {
        String[] values = {};
        Object result = this.util.convertValue(values, String[].class);
        assertEquals(String[].class, result.getClass());
        assertEquals(0, ((String[])result).length);
    }
    
    @Test
    public void test_empty_convert_to_int() {
        String[] values = {};
        Object result = this.util.convertValue(values, Integer.class);
        assertEquals(null, result);
    }
    
    @Test
    public void test_empty_convert_to_string() {
        String[] values = {};
        Object result = this.util.convertValue(values, String.class);
        assertEquals(null, result);
    }
}
