package me.donnior.sparkle.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import me.donnior.sparkle.core.route.RouteChecker;

import org.junit.Test;

public class RouteCheckerTest {

    @Test
    public void test() {
        RouteChecker c = new RouteChecker("/{name}/{action}/name");
        
        List<String> pathVarialbes = c.pathVariables();
        assertTrue(2 == pathVarialbes.size());
        assertEquals("name", pathVarialbes.get(0));
        
        assertEquals("/([^/]+)/([^/]+)/name", c.matcherRegexPatten());
        
        c = new RouteChecker("/name/action/name");
        
        pathVarialbes = c.pathVariables();
        assertTrue(0 == pathVarialbes.size());
        
        assertEquals("/name/action/name", c.matcherRegexPatten());
        
        try {
            new RouteChecker("/{na{me}action}/name");
            fail();
        } catch (RuntimeException e) {}

        try {
            c = new RouteChecker("/{name/action}/name");
            fail();
        } catch (RuntimeException e) {}

        try {
            c = new RouteChecker("/{name/action/name");
            fail();
        } catch (RuntimeException e) {}
        
        try {
            c = new RouteChecker("Item(s): {item1.test},{item2.qa},{item3.production}");
            fail();
        } catch (RuntimeException e) {
            
        }

    }

}
