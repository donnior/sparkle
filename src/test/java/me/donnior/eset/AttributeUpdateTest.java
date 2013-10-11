package me.donnior.eset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.google.common.collect.ImmutableMap;

public class AttributeUpdateTest {

    @Test
    public void testMain() {
        Map<String, Object> params = params();
        
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
        
        assertTrue(user.getAddress().size() == 2);
        assertEquals("beijing", user.getAddress().get(1).getCity());
        
        assertTrue(user.getStudiedPlaces().length == 2);
        assertEquals("beijing", user.getStudiedPlaces()[1].getCity());
    }

    private Map<String, Object> params() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", "123");
        params.put("age", "23");
        params.put("name", "donny");
        params.put("isAdmin", "true");
        params.put("desc", "my desc");
        params.put("nick", "donnior");
        params.put("isMale", "true");
        params.put("height", "170");
        params.put("weight", "120.0");
        List<String> mails = new ArrayList<String>();
        mails.add("donnior@gmail.com");
        params.put("mails", mails);
        
        List<Map<String,String>> address = new ArrayList<Map<String,String>>();
        address.add(ImmutableMap.of("state", "china", "city", "wuhan"));
        address.add(ImmutableMap.of("state", "china", "city", "beijing"));
        
        params.put("address", address);
        
        List<Map<String,String>> studiedPlaces = new ArrayList<Map<String,String>>();
        studiedPlaces.add(ImmutableMap.of("state", "china", "city", "wuhan"));
        studiedPlaces.add(ImmutableMap.of("state", "china", "city", "beijing"));
        
        params.put("studiedPlaces", studiedPlaces);
        return params;
    }

}
