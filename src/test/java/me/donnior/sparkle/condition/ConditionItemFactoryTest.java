package me.donnior.sparkle.condition;

import static org.junit.Assert.*;
import me.donnior.sparkle.core.route.condition.ConditionItem;
import me.donnior.sparkle.core.route.condition.ConditionItemFactory;
import me.donnior.sparkle.core.route.condition.EqualConditionItem;
import me.donnior.sparkle.core.route.condition.NotEqualConditionItem;
import me.donnior.sparkle.core.route.condition.NotNullConditionItem;

import org.junit.Test;

public class ConditionItemFactoryTest {

    @Test
    public void testCreateConditionItemFailed() {
        try{
            ConditionItemFactory.createItem(null);
            fail();
        }catch(RuntimeException re){}
        
        try{
            ConditionItemFactory.createItem("");
            fail();
        }catch(RuntimeException re){}
        
    }
    
    @Test
    public void testCreateEqualConditionItemFailed() {
        ConditionItem item = ConditionItemFactory.createItem("a=1");
        
        assertTrue(EqualConditionItem.class.equals(item.getClass()));
        assertEquals("a", item.getName());
        assertEquals("1", item.getValue());
    }
    
    @Test
    public void testCreateNotEqualConditionItemFailed() {
        ConditionItem item = ConditionItemFactory.createItem("a!=1");
        
        assertTrue(NotEqualConditionItem.class.equals(item.getClass()));
        assertEquals("a", item.getName());
        assertEquals("1", item.getValue());
    }
    
    @Test
    public void testCreateNotNullConditionItemFailed() {
        ConditionItem item = ConditionItemFactory.createItem("a");
        
        assertTrue(NotNullConditionItem.class.equals(item.getClass()));
        assertEquals("a", item.getName());
        assertNull(item.getValue());
    }
    
    @Test
    public void testNewConditionFactory(){
        assertNotNull(new ConditionItemFactory());
    }
       
}
