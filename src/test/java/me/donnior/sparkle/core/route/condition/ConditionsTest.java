package me.donnior.sparkle.core.route.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import me.donnior.sparkle.core.SimpleWebRequest;
import me.donnior.web.adapter.HttpServletRequestAdapter;

import org.junit.Test;

public class ConditionsTest {

    @Test
    public void testHeaderCondition() {

        HeaderCondition c = new HeaderCondition(new String[]{"a=1","b!=1", "c"});
        
        boolean result = c.match(new SimpleWebRequest(new HttpServletRequestAdapter(){
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
        },null));
        
        assertTrue(result);
        
        result = c.match(new SimpleWebRequest(new HttpServletRequestAdapter(){
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
        },null));
        
        assertFalse(result);
    }
    
    @Test
    public void testParamCondition() {

        ParamCondition c = new ParamCondition(new String[]{"a=1","b!=1", "c"});
        
        boolean result = c.match(new SimpleWebRequest(new HttpServletRequestAdapter(){
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
        },null));
        
        assertTrue(result);
        
        result = c.match(new SimpleWebRequest(new HttpServletRequestAdapter(){
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
        },null));
        
        assertFalse(result);
    }
    
    @Test
    public void testConsumeCondition() {

        ConsumeCondition c = new ConsumeCondition(new String[]{"a=1","b!=1", "c"});
        try{
            c.match(null);
            fail();
        }catch(RuntimeException re){
            assertEquals("not implemented yet", re.getMessage());
        }
    }
    
    
    @Test
    public void testConditionToString() {

        HeaderCondition c = new HeaderCondition(new String[]{"a=1","b!=1", "c"});
        
        assertEquals("\"a=1,b!=1,c\"", c.toString());
    }
           
}
