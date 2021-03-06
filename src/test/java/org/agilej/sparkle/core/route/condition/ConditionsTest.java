package org.agilej.sparkle.core.route.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import org.agilej.web.adapter.WebRequestAdapter;

import org.junit.Test;

public class ConditionsTest {

    @Test
    public void test_header_condition() {

        HeaderCondition c = new HeaderCondition(new String[]{"a==1","b!=1", "c"});
        
        boolean result = c.match(new WebRequestAdapter(){
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
        
        result = c.match(new WebRequestAdapter(){
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
    public void test_param_condition() {

        ParamCondition c = new ParamCondition(new String[]{"a==1","b!=1", "c"});
        
        boolean result = c.match(new WebRequestAdapter(){
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
        
        result = c.match(new WebRequestAdapter(){
            @Override
            public String getParameter(String paramName) {
                if("a".equals(paramName)){
                    return "1";
                }
                if("b".equals(paramName)){
                    return "1";
                }
                if("c".endsWith(paramName)){
                    return "anything";
                }
                return null;
                
            }
            
        });
        
        assertFalse(result);
    }
    
    @Test
    public void test_consume_condition() {

        ConsumeCondition c = new ConsumeCondition(new String[]{"a==1","b!=1", "c"});
        try{
            c.match(null);
            fail();
        }catch(RuntimeException re){
            assertEquals("not implemented yet", re.getMessage());
        }
    }
    
    
    @Test
    public void test_condition_to_string() {

        HeaderCondition c = new HeaderCondition(new String[]{"a==1","b!=1", "c"});
        
        assertEquals("\"a==1,b!=1,c\"", c.toString());
    }
           
}
