package me.donnior.sparkle.route;

import static org.junit.Assert.*;
import me.donnior.sparkle.core.route.RouteBuilder;
import me.donnior.sparkle.core.route.RouteMachter;
import me.donnior.sparkle.core.route.RouterImpl;
import me.donnior.web.adapter.GetHttpServletRequest;

import org.junit.Test;

public class RouteMatcherTest {

    @Test
    public void test(){
        RouterImpl router = new RouterImpl();
        router.match("/user").to("user#show");
        
        RouteMachter matcher = new RouteMachter(router);

        RouteBuilder rb = matcher.match(new GetHttpServletRequest("/accounts"));
        assertNull(rb);
        
        rb = matcher.match(new GetHttpServletRequest("/user"));
        assertNotNull(rb);
        assertEquals("/user", rb.getPathPattern());
        assertEquals("show", rb.getActionName());
        
        router.match("/user").matchParams("profile=1").to("user#profile");
        
        rb = matcher.match(new GetHttpServletRequest("/user"){
            @Override
            public String getParameter(String name) {
                if("profile".equals(name)){
                    return "1";
                }
                return null;
            }
        });
        assertNotNull(rb);
        assertEquals("/user", rb.getPathPattern());
        assertEquals("profile", rb.getActionName());
        
    }
    
}
