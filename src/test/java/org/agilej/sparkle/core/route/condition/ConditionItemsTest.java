package org.agilej.sparkle.core.route.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConditionItemsTest {

    @Test
    public void test_equal_condition_item() {
        EqualConditionItem ci = new EqualConditionItem(new String[]{"a", "1"});
        
        assertEquals("a", ci.getName());
        assertFalse(ci.match("2"));
        assertTrue(ci.match("1"));
    }
    
    @Test
    public void test_not_equal_condition_item() {
        NotEqualConditionItem ci = new NotEqualConditionItem(new String[]{"a", "1"});
        
        assertEquals("a", ci.getName());
        assertFalse(ci.match("1"));
        assertTrue(ci.match("2"));
    }

    @Test
    public void test_not_null_condition_item() {
        NotNullConditionItem ci = new NotNullConditionItem("a");
        
        assertEquals("a", ci.getName());
        assertFalse(ci.match(null));
        assertTrue(ci.match("1"));
    }

    
}
