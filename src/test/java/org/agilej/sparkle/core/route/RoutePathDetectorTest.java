package org.agilej.sparkle.core.route;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertNull;

public class RoutePathDetectorTest {

    @Test
    public void testCreate() {
        RoutePathDetector c = new RoutePathDetector("/{name}/{handler}/name");

        try {
            new RoutePathDetector("/{na{me}handler}/name");
            fail();
        } catch (RuntimeException e) {

        }

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
    public void test_match_path(){

        testMatchTrue("/user/{id}", "/user/1");
        testMatchTrue("/user/{id}", "/user/1/");
        testMatchTrue("/user/{ id}", "/user/1/");
        testMatchTrue("/user/{id  }", "/user/1/");
        testMatchTrue("/user/{ id }", "/user/1/");
        testMatchTrue("/user/{1}", "/user/1/");
        testMatchFalse("/user/{id}", "/user/1/names");

        testMatchTrue("/user/{id}/profile/{module}", "/user/donnior/profile/header");
        testMatchFalse("/user/{id}/profile/{module}", "/users/1");

        testMatchTrue("/", "/");
    }

    private void testMatchTrue(String template, String path) {
        assertTrue(new RoutePathDetector(template).matches(path));
    }

    private void testMatchFalse(String template, String path) {
        assertFalse(new RoutePathDetector(template).matches(path));
    }

    @Test
    public void test_extract_path_variable_names(){
        RoutePathDetector rb = new RoutePathDetector("/user/{ id }/profile/{ module }");

        List<String> names = rb.pathVariableNames();
        assertTrue(2 == names.size());
        assertEquals("id", names.get(0));
        assertEquals("module", names.get(1));

        assertTrue(0 == new RoutePathDetector("/home").pathVariableNames().size());
    }

    @Test
    public void test_extract_path_variable_values(){
        RoutePathDetector rb = new RoutePathDetector("/user/{ id }/profile/{ module }");

        Map<String, String> values = rb.pathVariables("/user/donnior/profile/header");

        assertTrue(values.containsKey("id"));
        assertTrue(values.containsKey("module"));

        assertEquals("donnior", values.get("id"));
        assertEquals("header", values.get("module"));
    }

    @Test
    public void test_pattern_description() {
        RoutePathDetector c = new RoutePathDetector("/{name}/{handler}/name");
        assertEquals("^/([^/]+?)/([^/]+?)/name/?$", c.matcherPattenDescription());

        c = new RoutePathDetector("/user/dashboard");
        assertEquals("^/user/dashboard/?$", c.matcherPattenDescription());
    }
}
