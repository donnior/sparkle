package me.donnior.sparkle.core.route.condition;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import me.donnior.sparkle.core.route.ConditionMatchs;

import org.junit.Test;

public class ConditionMatchsTest {

    @Test
    public void test_condition_match(){
        
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
