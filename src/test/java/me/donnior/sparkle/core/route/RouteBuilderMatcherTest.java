package me.donnior.sparkle.core.route;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.servlet.ServletWebRequest;
import me.donnior.web.adapter.GetHttpServletRequest;

import org.junit.Test;

public class RouteBuilderMatcherTest {

    @Test
    public void test_match_path_failed(){
        WebRequest request = new ServletWebRequest(new GetHttpServletRequest("/user1"), null);
        RouteBuilder rb = new RouteBuilder("/user");
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
        
    }
    
    @Test
    public void test_match_method_failed(){
        WebRequest request = new ServletWebRequest(new GetHttpServletRequest("/user"),null);
        RouteBuilder rb = new RouteBuilder("/user");
        rb.withPost();
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
    }
    
    @Test
    public void test_match_params_failed(){
        RouteBuilder rb = new RouteBuilder("/user");
        rb.matchParams("a=1");
        WebRequest request = new ServletWebRequest(new GetHttpServletRequest("/user"){
            @Override
            public String getParameter(String name) {
                if("a".equals(name)){
                    return "2";
                }
                return null;
            }
        }, null);
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
    }
  
    @Test
    public void test_match_headers_failed(){
        RouteBuilder rb = new RouteBuilder("/user");
        rb.matchHeaders("a=1");
        WebRequest request = new ServletWebRequest(new GetHttpServletRequest("/user"){
            @Override
            public String getHeader(String name) {
                if("a".equals(name)){
                    return "2";
                }
                return null;            
            }
        }, null);
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
    }
    
    @Test
    public void test_all_matched(){
        RouteBuilder rb = new RouteBuilder("/user");
        rb.matchHeaders("a=1");
        WebRequest request = new ServletWebRequest(new GetHttpServletRequest("/user"){
            @Override
            public String getHeader(String name) {
                if("a".equals(name)){
                    return "1";
                }
                return null;
            }
        }, null);
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertTrue(matcher.match());
    }    
}
