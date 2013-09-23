package me.donnior.eset;

import java.util.HashMap;
import java.util.Map;

import me.donnior.fava.FHashMap;
import me.donnior.fava.MConsumer;

import org.junit.Test;

public class ParsedParamsTest {

    @Test
    public void testParsedParams(){
        
        ParsedParams pp = new ParsedParams(params());
//        Object obj = pp.getOrCreateNodeByPath("user[address][0][state]", PathType.HASH);
//        pp.process("age", new String[]{"30"});
//        pp.process("user[address][0][state]", new String[]{"china"});
//        pp.process("user[address][0][city]", new String[]{"wuhan"});
//        pp.process("user[address][1][state]", new String[]{"china"});
//        pp.process("user[address][0][city]", new String[]{"beijing"});
        
//        System.out.println("size : " + pp.getParams().size());
//        System.out.println("user : " + pp.get("user"));
//        System.out.println("user[address] : " + pp.get("user[address]"));
//        System.out.println("user[address][0] : " + pp.get("user[address][0]"));
//        System.out.println("user[address][0][state] : " + pp.get("user[address][0][state]"));
//        
//        System.out.println("--------");
        new FHashMap<String, Object>(pp.getParams()).each(new MConsumer<String, Object>() {
            
            @Override
            public void apply(String key, Object value) {
                System.out.println(key + " : "+ value);
            }
        });
        
    }
 
    private Map<String, String[]> params() {
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("page", new String[]{"1"});
        params.put("user[age]", new String[]{"23"});
        params.put("user[name]", new String[]{"donny"});
        params.put("user[isAdmin]", new String[]{"true"});

        params.put("user[address][0][state]", new String[]{"china"});
        params.put("user[address][0][city]", new String[]{"wuhan"});
        params.put("user[address][1][state]", new String[]{"china"});
        params.put("user[address][1][city]", new String[]{"shanghai"});
        
        params.put("user[mails][0]", new String[]{"dmx@qq.com"});
        params.put("user[mails][1]", new String[]{"dm.x@gmail.com"});
        params.put("user[mails][2]", new String[]{"xdm@aa.com"});
        
        return params;
    }
    
}
