package org.agilej.sparkle.core.route;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class RoutePathDetectorTest {

    @Test
    public void test() {
        RoutePathDetector c = new RoutePathDetector("/{name}/{handler}/name");
        
        List<String> pathVariableNames = c.pathVariableNames();
        assertTrue(2 == pathVariableNames.size());
        assertEquals("name", pathVariableNames.get(0));
        
        assertEquals("/([^/]+)/([^/]+)/name", c.matcherPattenDescription());
        
        c = new RoutePathDetector("/name/handler/name");
        
        pathVariableNames = c.pathVariableNames();
        assertTrue(0 == pathVariableNames.size());
        
        assertEquals("/name/handler/name", c.matcherPattenDescription());
        
        try {
            new RoutePathDetector("/{na{me}handler}/name");
            fail();
        } catch (RuntimeException e) {}

        try {
            c = new RoutePathDetector("/{name/handler}/name");
            fail();
        } catch (RuntimeException e) {}

        try {
            c = new RoutePathDetector("/{name/handler/name");
            fail();
        } catch (RuntimeException e) {}
        
        try {
            c = new RoutePathDetector("Item(s): {item1.test},{item2.qa},{item3.production}");
            fail();
        } catch (RuntimeException e) {
            
        }

    }

    @Test
    public void test_matches(){
        RoutePathDetector rb = new RoutePathDetector("/user/{id}/profile/{module}");

        assertEquals("/user/([^/]+)/profile/([^/]+)", rb.matcherPattenDescription());

        assertTrue(rb.matches("/user/donnior/profile/header"));
        assertFalse(rb.matches("/users/1"));

    }

    @Test
    public void test_extract_path_variable_values(){
        RoutePathDetector rb = new RoutePathDetector("/user/{id}/profile/{module}");

        Map<String, String> values = rb.pathVariableNames("/user/donnior/profile/header");

        assertTrue(values.containsKey("id"));
        assertTrue(values.containsKey("module"));

        assertEquals("donnior", values.get("id"));
        assertEquals("header", values.get("module"));
    }
}
