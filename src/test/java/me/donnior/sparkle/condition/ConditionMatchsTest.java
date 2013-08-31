package me.donnior.sparkle.condition;

import static org.junit.Assert.*;
import me.donnior.sparkle.core.route.ConditionMatchs;

import org.junit.Test;

public class ConditionMatchsTest {

    @Test
    public void testConditionMatchs(){
        
        ConditionMatchs dft = ConditionMatchs.DEFAULT_SUCCEED;
        ConditionMatchs explicit = ConditionMatchs.EXPLICIT_SUCCEED;
        ConditionMatchs failed = ConditionMatchs.FAILED;
        
        assertFalse(dft.isExplicitMatch());
        assertTrue(dft.succeed());
        
        assertTrue(explicit.isExplicitMatch());
        assertTrue(explicit.succeed());
        
        assertTrue(failed.isExplicitMatch());
        assertFalse(failed.succeed());
        
    }
    
}
