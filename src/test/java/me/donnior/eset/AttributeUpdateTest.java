package me.donnior.eset;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import me.donnior.eset.EntityUpdater;

import org.junit.Test;

public class AttributeUpdateTest {

    @Test
    public void testMain() {
        Map<String, String[]> params = params();
        
        User user = new User();
        user.setId(22);
        
        new EntityUpdater().updateAttribute(user, params);
        
        assertTrue(22 == user.getId());
        assertTrue(23 == user.getAge());
        
        assertEquals("donny", user.getName());
        assertEquals("donnior", user.getNickName());

        assertTrue(user.isMale());
        assertFalse(user.isAdmin());
        assertNull(user.getDesc());
        
        assertTrue(user.getHeight() == 170);
        assertTrue(user.getWeight() == 120.0);
    }

    private Map<String, String[]> params() {
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("id", new String[]{"123"});
        params.put("age", new String[]{"23"});
        params.put("name", new String[]{"donny"});
        params.put("isAdmin", new String[]{"true"});
        params.put("desc", new String[]{"my desc"});
        params.put("nick", new String[]{"donnior"});
        params.put("isMale", new String[]{"true"});
        params.put("height", new String[]{"170"});
        params.put("weight", new String[]{"120.0"});
        return params;
    }

}
