package me.donnior.eset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class ArgMapTest {

    @Test
    public void testParsedParams(){
        
        Map<String, String[]> params = params();
        
        ArgMap pp = new ArgMap(params);

        System.out.println(pp.getAll());
        System.out.println(pp.get("user"));
        
        assertTrue(pp.get("account") instanceof Map);
        assertTrue(pp.get("user") instanceof Map);
        assertTrue(pp.get("user.father") instanceof Map);
        assertTrue(pp.get("user.address") instanceof List);
        assertTrue(pp.get("user.mails") instanceof List);

        assertEquals("dmx", (String)pp.get("account.login"));
        
        List mails = (List)pp.get("user.mails");
        assertTrue(mails.size() == 4);
        assertNull(mails.get(2));
        assertEquals("dm.x@gmail.com", mails.get(1));
        
        List<Map> address = (List<Map>)pp.get("user.address");
        assertTrue(address.size() == 4);
        assertTrue(address.get(1) == null);
        assertEquals("shanghai", address.get(0).get("city"));
        assertEquals("wuhan", address.get(3).get("city"));
        
        assertEquals("shanghai", pp.get("user.address[0].city"));
        assertEquals("wuhan", pp.get("user.address[3].city"));
        
        assertNull(pp.get("user.address[0].state"));
        
//        System.out.println(pp.get("user", Map.class));
    }
 
    private Map<String, String[]> params() {
        Map<String, String[]> params = new HashMap<String, String[]>();

        params.put("user.age", new String[]{"23"});
        params.put("user.name", new String[]{"donny"});
        params.put("user.isAdmin", new String[]{"true"});
//
        params.put("user.address[3].state", new String[]{"china"});
        params.put("user.address[3].city", new String[]{"wuhan"});
        params.put("user.address[2].state", new String[]{"china"});
        params.put("user.address[0].city", new String[]{"shanghai"});
//  
        params.put("user.mails[0]", new String[]{"dmx@qq.com"});
        params.put("user.mails[1]", new String[]{"dm.x@gmail.com"});
        params.put("user.mails[3]", new String[]{"xdm@aa.com"});
        params.put("user.father.name", new String[]{"xdz"});
        params.put("user.father.age", new String[]{"60"});
//        
        params.put("table[0][0]", new String[]{"00"});
        params.put("table[0][1]", new String[]{"01"});
        params.put("table[1][0]", new String[]{"10"});
        params.put("table[1][1]", new String[]{"11"});
//
        params.put("account.login", new String[]{"dmx"});
//        
        return params;
    }
 
}
