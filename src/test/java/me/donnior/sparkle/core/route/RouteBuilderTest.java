package me.donnior.sparkle.core.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.WebRequest;
import me.donnior.web.adapter.HttpServletRequestAdapter;
import me.donnior.web.adapter.ServletWebRequest;

import org.junit.Test;

public class RouteBuilderTest {

    @Test
    public void test_create(){
        RouteBuilder rb = new RouteBuilder("/user/{id}");

        assertEquals(HTTPMethod.GET, rb.getHttpMethod());
        assertTrue(rb.matchMethod(HTTPMethod.GET));
        assertNull(rb.getControllerName());
        assertNull(rb.getActionName());
        assertEquals("/user/{id}", rb.getPathPattern());
        
        assertEquals("/user/([^/]+)", rb.getMatchPatten().pattern());
        
        assertTrue(rb.matchPath("/user/donnior"));
        assertFalse(rb.matchPath("/users/1"));
        
        rb = rb.withGet();
        assertEquals(HTTPMethod.GET, rb.getHttpMethod());
    }

    @Test
    public void test_create_without_condition(){
        RouteBuilder rb = new RouteBuilder("/user/{id}");

        rb.withPost().matchHeaders((String[])null).matchParams((String[])null).matchConsumes((String[])null).to("user#show");
        
        assertTrue(rb.matchMethod(HTTPMethod.POST));
        assertEquals("user", rb.getControllerName());
        assertEquals("show", rb.getActionName());
        
        assertTrue(rb.matchPath("/user/donnior"));
        assertFalse(rb.matchPath("/users/1"));
        
        WebRequest request = new ServletWebRequest(matchedRequest(),null);
        assertEquals(ConditionMatchs.DEFAULT_SUCCEED, rb.matchConsume(request));
        assertEquals(ConditionMatchs.DEFAULT_SUCCEED, rb.matchParam(request));
        assertEquals(ConditionMatchs.DEFAULT_SUCCEED, rb.matchHeader(request));
    }
    
    @Test
    public void test_create_with_condition_and_matched(){
        RouteBuilder rb = new RouteBuilder("/user/{id}");
        rb.matchParams("a=1").matchHeaders("token").matchConsumes("applicationType").to("user#show");
        
        assertEquals("user", rb.getControllerName());
        assertEquals("show", rb.getActionName());
        
        WebRequest request = new ServletWebRequest(matchedRequest(),null);
//        assertEquals(ConditionMatchs.DEFAULT_SUCCEED, rb.matchConsume(request));
        assertEquals(ConditionMatchs.EXPLICIT_SUCCEED, rb.matchParam(request));
        assertEquals(ConditionMatchs.EXPLICIT_SUCCEED, rb.matchHeader(request));
    }
    
    @Test
    public void test_create_with_condition_but_not_matched(){
        RouteBuilder rb = new RouteBuilder("/user/{id}");
        rb.matchParams("a=1").matchHeaders("token").matchConsumes("applicationType").to("user#show");
        
        assertEquals("user", rb.getControllerName());
        assertEquals("show", rb.getActionName());
        
        WebRequest request = new ServletWebRequest(notMatchedRequest(),null);
//        assertEquals(ConditionMatchs.DEFAULT_SUCCEED, rb.matchConsume(request));
        assertEquals(ConditionMatchs.FAILED, rb.matchParam(request));
        assertEquals(ConditionMatchs.FAILED, rb.matchHeader(request));
    }    
   
    @Test
    public void test_illegal_route(){
        RouteBuilder rb = new RouteBuilder("/user/{id}");

        try{
            rb.to("user#show#id");
            fail();
        }catch(RuntimeException re){
            assertEquals(
                    "route's 'to' part 'user#show#id' is illegal, it must be 'controller#action' or just 'controller'", 
                    re.getMessage());
        }
        
        try{
            rb.to(null);
            fail();
        }catch(RuntimeException re){
            assertEquals(
                    "route's 'to' part 'null' is illegal, it must be 'controller#action' or just 'controller'", 
                    re.getMessage());
        }
        
        
    }
    
    private HttpServletRequestAdapter matchedRequest() {
        return new HttpServletRequestAdapter(){
            @Override
            public String getHeader(String arg0) {
                if(arg0.equals("token")){
                    return "not null";
                }
                return super.getHeader(arg0);
            }
            
            @Override
            public String getParameter(String arg0) {
                if(arg0.equals("a")){
                    return "1";
                }
                return super.getParameter(arg0);
            }
            
            
        };
    }   
    
    private HttpServletRequestAdapter notMatchedRequest() {
        return new HttpServletRequestAdapter(){
            @Override
            public String getHeader(String arg0) {
                if(arg0.equals("token")){
                    return null;
                }
                return super.getHeader(arg0);
            }
            
            @Override
            public String getParameter(String arg0) {
                if(arg0.equals("a")){
                    return "2";
                }
                return super.getParameter(arg0);
            }
            
            
        };
    }   
}
