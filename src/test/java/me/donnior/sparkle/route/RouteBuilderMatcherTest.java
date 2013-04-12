package me.donnior.sparkle.route;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.servlet.http.HttpServletRequest;

import me.donnior.web.adapter.GetHttpServletRequest;

import org.junit.Test;

public class RouteBuilderMatcherTest {

    @Test
    public void test_match_path_failed(){
        HttpServletRequest request = new GetHttpServletRequest("/user1");
        RouteBuilder rb = new RouteBuilder("/user");
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
        
    }
    
    @Test
    public void test_match_method_failed(){
        HttpServletRequest request = new GetHttpServletRequest("/user");
        RouteBuilder rb = new RouteBuilder("/user");
        rb.withPost();
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
    }
    
    @Test
    public void test_match_params_failed(){
        RouteBuilder rb = new RouteBuilder("/user");
        rb.matchParams("a=1");
        HttpServletRequest request = new GetHttpServletRequest("/user"){
            @Override
            public String getParameter(String name) {
                if("a".equals(name)){
                    return "2";
                }
                return null;
            }
        };
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
    }
  
    @Test
    public void test_match_headers_failed(){
        RouteBuilder rb = new RouteBuilder("/user");
        rb.matchHeaders("a=1");
        HttpServletRequest request = new GetHttpServletRequest("/user"){
            @Override
            public String getHeader(String name) {
                if("a".equals(name)){
                    return "2";
                }
                return null;            
            }
        };
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
    }
    
    @Test
    public void test_all_matched(){
        RouteBuilder rb = new RouteBuilder("/user");
        rb.matchHeaders("a=1");
        HttpServletRequest request = new GetHttpServletRequest("/user"){
            @Override
            public String getHeader(String name) {
                if("a".equals(name)){
                    return "1";
                }
                return null;
            }
        };
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertTrue(matcher.match());
    }    
}
