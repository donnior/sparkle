package me.donnior.sparkle.core.route.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ConditionItemsTest {

    @Test
    public void testEqualConditionItem() {
        EqualConditionItem ci = new EqualConditionItem(new String[]{"a", "1"});
        
        assertEquals("a", ci.getName());
        assertFalse(ci.match("2"));
        assertTrue(ci.match("1"));
    }
    
    @Test
    public void testNotEqualConditionItem() {
        NotEqualConditionItem ci = new NotEqualConditionItem(new String[]{"a", "1"});
        
        assertEquals("a", ci.getName());
        assertFalse(ci.match("1"));
        assertTrue(ci.match("2"));
    }

    @Test
    public void testNotNullConditionItem() {
        NotNullConditionItem ci = new NotNullConditionItem("a");
        
        assertEquals("a", ci.getName());
        assertFalse(ci.match(null));
        assertTrue(ci.match("1"));
    }

    
}
