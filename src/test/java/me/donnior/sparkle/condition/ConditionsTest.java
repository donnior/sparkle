package me.donnior.sparkle.condition;

import static org.junit.Assert.*;

import me.donnior.web.adapter.HttpServletRequestAdapter;

import org.junit.Test;

public class ConditionsTest {

    @Test
    public void testHeaderCondition() {

        HeaderCondition c = new HeaderCondition(new String[]{"a=1","b!=1", "c"});
        
        boolean result = c.match(new HttpServletRequestAdapter(){
            @Override
            public String getHeader(String headerKey) {
                if("a".equals(headerKey)){
                    return "1";
                }
                if("b".equals(headerKey)){
                    return "2";
                }
                if("c".endsWith(headerKey)){
                    return "anything";
                }
                return null;
                
            }
        });
        
        assertTrue(result);
        
        result = c.match(new HttpServletRequestAdapter(){
            @Override
            public String getHeader(String headerKey) {
                if("a".equals(headerKey)){
                    return "1";
                }
                if("b".equals(headerKey)){
                    return "1";
                }
                if("c".endsWith(headerKey)){
                    return "anything";
                }
                return null;
                
            }
        });
        
        assertFalse(result);
    }
    
    @Test
    public void testParamCondition() {

        ParamCondition c = new ParamCondition(new String[]{"a=1","b!=1", "c"});
        
        boolean result = c.match(new HttpServletRequestAdapter(){
            @Override
            public String getParameter(String paramName){
                if("a".equals(paramName)){
                    return "1";
                }
                if("b".equals(paramName)){
                    return "2";
                }
                if("c".endsWith(paramName)){
                    return "anything";
                }
                return null;
                
            }
        });
        
        assertTrue(result);
        
        result = c.match(new HttpServletRequestAdapter(){
            @Override
            public String getHeader(String headerKey) {
                if("a".equals(headerKey)){
                    return "1";
                }
                if("b".equals(headerKey)){
                    return "1";
                }
                if("c".endsWith(headerKey)){
                    return "anything";
                }
                return null;
                
            }
        });
        
        assertFalse(result);
    }
    
    @Test
    public void testConditionToString() {

        HeaderCondition c = new HeaderCondition(new String[]{"a=1","b!=1", "c"});
        
        assertEquals("\"a=1,b!=1,c\"", c.toString());
    }
           
}
