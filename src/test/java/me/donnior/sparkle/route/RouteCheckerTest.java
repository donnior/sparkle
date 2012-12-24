package me.donnior.sparkle.route;

import static org.junit.Assert.*;

import org.junit.Test;

public class RouteCheckerTest {

    @Test
    public void test() {
        RouteChecker c = new RouteChecker("/{name}/{action}/name");
        
        
        assertTrue(c.isCorrectRoute("/{name}/{action}/name"));
        
        c = new RouteChecker("/name/action/name");
        assertTrue(c.isCorrectRoute("/name/action/name"));
        
        try {
            c.isCorrectRoute("/{na{me}action}/name");
            fail();
        } catch (RuntimeException e) {
            
        }

        try {
            c = new RouteChecker("/{name/action}/name");
            c.isCorrectRoute("/{name/action}/name");
            fail();
        } catch (RuntimeException e) {
            
        }

        
        try {
            c = new RouteChecker("Item(s): {item1.test},{item2.qa},{item3.production}");
            c.isCorrectRoute("Item(s): {item1.test},{item2.qa},{item3.production}");
            fail();
        } catch (RuntimeException e) {
            
        }

        
        
        
    }

}
