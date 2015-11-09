package org.agilej.sparkle.core.route.condition;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ConditionItemFactoryTest {

    @Test
    public void test_create_condition_item_failed() {
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
    public void test_create_equal_condition_item() {
        ConditionItem item = ConditionItemFactory.createItem("a=1");
        
        assertTrue(EqualConditionItem.class.equals(item.getClass()));
        assertEquals("a", item.getName());
        assertEquals("1", item.getValue());
    }
    
    @Test
    public void test_create_not_equal_condition_item() {
        ConditionItem item = ConditionItemFactory.createItem("a!=1");
        
        assertTrue(NotEqualConditionItem.class.equals(item.getClass()));
        assertEquals("a", item.getName());
        assertEquals("1", item.getValue());
    }
    
    @Test
    public void test_create_not_null_condition_item() {
        ConditionItem item = ConditionItemFactory.createItem("a");
        
        assertTrue(NotNullConditionItem.class.equals(item.getClass()));
        assertEquals("a", item.getName());
        assertNull(item.getValue());
    }
    
    @Test
    public void test_new_condition_factory(){
        assertNotNull(new ConditionItemFactory());
    }
       
}
