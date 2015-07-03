package me.donnior.sparkle.core.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import me.donnior.sparkle.HTTPMethod;
import me.donnior.sparkle.WebRequest;
import me.donnior.web.adapter.WebRequestAdapter;

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
    public void test_create_root(){
        System.out.println("test for root");
        RouteBuilder rb = new RouteBuilder("/");

        assertEquals(HTTPMethod.GET, rb.getHttpMethod());
        assertTrue(rb.matchMethod(HTTPMethod.GET));
        assertNull(rb.getControllerName());
        assertNull(rb.getActionName());
        assertEquals("/", rb.getPathPattern());
        
        assertEquals("/", rb.getMatchPatten().pattern());
        
        assertTrue(rb.matchPath("/"));
        assertFalse(rb.matchPath("/users"));
        
        rb = rb.withGet();
        assertEquals(HTTPMethod.GET, rb.getHttpMethod());
        
    }
    
    @Test
    public void test_get_path_variables(){
        RouteBuilder rb = new RouteBuilder("/user/{id}/profile/{module}");

        assertEquals(HTTPMethod.GET, rb.getHttpMethod());
        assertTrue(rb.matchMethod(HTTPMethod.GET));
        assertNull(rb.getControllerName());
        assertNull(rb.getActionName());
        assertEquals("/user/{id}/profile/{module}", rb.getPathPattern());
        
        assertEquals("/user/([^/]+)/profile/([^/]+)", rb.getMatchPatten().pattern());
        
        assertTrue(rb.matchPath("/user/donnior/profile/header"));
//        assertFalse(rb.matchPath("/users/1"));
        
        
        System.out.println(rb.extractPathVariableValues("/user/donnior/profile/header"));
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
        
        WebRequest request = matchedRequest();
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
        
        WebRequest request = matchedRequest();
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
        
        WebRequest request = notMatchedRequest();
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
                    "Route's 'to' part ['user#show#id'] is illegal, it must be 'controller#action' or just 'controller'",
                    re.getMessage());
        }
        
        try{
            rb.to((String)null);
            fail();
        }catch(RuntimeException re){
            assertEquals(
                    "Route's 'to' part ['null'] is illegal, it must be 'controller#action' or just 'controller'",
                    re.getMessage());
        }
        
        
    }
    
    private WebRequest matchedRequest() {
        return new WebRequestAdapter(){
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
    
    private WebRequest notMatchedRequest() {
        return new WebRequestAdapter(){
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
