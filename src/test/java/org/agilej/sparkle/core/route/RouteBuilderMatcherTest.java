package org.agilej.sparkle.core.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.agilej.sparkle.WebRequest;
import org.agilej.web.adapter.GetWebRequest;

import org.junit.Test;

public class RouteBuilderMatcherTest {

    @Test
    public void test_match_path_succeed(){
        WebRequest request = new GetWebRequest("/user");
        RouteBuilder rb = new RouteBuilder("/user");
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertTrue(matcher.match());
        
    }
    
    @Test
    public void test_match_path_failed(){
        WebRequest request = new GetWebRequest("/user1");
        RouteBuilder rb = new RouteBuilder("/user");
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
        
    }
    
    @Test
    public void test_match_method_failed(){
        WebRequest request = new GetWebRequest("/user");
        RouteBuilder rb = new RouteBuilder("/user");
        rb.withPost();
        
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertFalse(matcher.match());
    }
    
    @Test
    public void test_match_params_failed(){
        RouteBuilder rb = new RouteBuilder("/user");
        rb.matchParams("a==1");
        WebRequest request = new GetWebRequest("/user"){
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
        rb.matchHeaders("a==1");
        WebRequest request = new GetWebRequest("/user"){
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
        rb.matchHeaders("a==1");
        WebRequest request = new GetWebRequest("/user"){
            @Override
            public String getHeader(String name) {
                if("a".equals(name)){
                    return "1";
                }
                return null;
            }
        };

        assertTrue(rb.hasHeaderCondition());
        RouteBuilderMatcher matcher = new RouteBuilderMatcher(rb, request);
        assertTrue(matcher.match());

        MatchedCondition[] matchedConditions = matcher.matchedExplicitConditions();
        assertEquals(1, matchedConditions.length);
        assertEquals(ConditionMatchs.EXPLICIT_SUCCEED, matchedConditions[0]);
    }    
}
