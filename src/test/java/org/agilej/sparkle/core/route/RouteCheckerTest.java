package org.agilej.sparkle.core.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Test;

public class RouteCheckerTest {

    @Test
    public void test() {
        RouteChecker c = new RouteChecker("/{name}/{handler}/name");
        
        List<String> pathVarialbes = c.pathVariables();
        assertTrue(2 == pathVarialbes.size());
        assertEquals("name", pathVarialbes.get(0));
        
        assertEquals("/([^/]+)/([^/]+)/name", c.matcherRegexPatten());
        
        c = new RouteChecker("/name/handler/name");
        
        pathVarialbes = c.pathVariables();
        assertTrue(0 == pathVarialbes.size());
        
        assertEquals("/name/handler/name", c.matcherRegexPatten());
        
        try {
            new RouteChecker("/{na{me}handler}/name");
            fail();
        } catch (RuntimeException e) {}

        try {
            c = new RouteChecker("/{name/handler}/name");
            fail();
        } catch (RuntimeException e) {}

        try {
            c = new RouteChecker("/{name/handler/name");
            fail();
        } catch (RuntimeException e) {}
        
        try {
            c = new RouteChecker("Item(s): {item1.test},{item2.qa},{item3.production}");
            fail();
        } catch (RuntimeException e) {
            
        }

    }

}
