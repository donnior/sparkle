package me.donnior.sparkle.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TupleTest {

    @Test
    public void testTuple2(){
        Tuple2 first = Tuple.of("one", 1);
        assertTrue(String.class.isAssignableFrom(first.get1().getClass()));
        assertTrue(Integer.class.isAssignableFrom(first.get2().getClass()));
        
        assertTrue(first.equals(new Tuple2("one", 1)));
        assertFalse(first.equals(new Tuple2("one", 2)));
        assertFalse(first.equals(new Tuple1("one")));
        assertFalse(first.equals(null));
        
        assertTrue(first.hashCode() == new Tuple2("one", 1).hashCode());
        assertFalse(first.hashCode() == new Tuple2("one", 2).hashCode());
        
        
    }
    
    @Test
    public void testTuple1(){
        Tuple1 first = Tuple.of("one");
        assertTrue(String.class.isAssignableFrom(first.get1().getClass()));
        
        assertTrue(first.equals(new Tuple1("one")));
        assertFalse(first.equals(new Tuple1("two")));
        assertFalse(first.equals(new Tuple2("two",2)));
        assertFalse(first.equals(null));
        
        assertTrue(first.hashCode() == new Tuple1("one").hashCode());
        assertFalse(first.hashCode() == new Tuple1("two").hashCode());
        
    }
    
    
    }
