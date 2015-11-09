package org.agilej.eset;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.agilej.fava.FHashMap;
import org.agilej.fava.MConsumer;

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
        
//        assertTrue(pp.get("user").isMap());
//        assertTrue(pp.get("user[address]").isCollection());
//        assertTrue(pp.get("user[mails]").isCollection());
//        
        Map user = (Map)pp.get("user");
        Object father = user.get("father");
        List address = (List)user.get("address");
        System.out.println(address.get(1));
//        
    }
 
    private Map<String, String[]> params() {
        Map<String, String[]> params = new HashMap<String, String[]>();
        params.put("page", new String[]{"1"});
        params.put("user[age]", new String[]{"23"});
        params.put("user[name]", new String[]{"donny"});
        params.put("user[isAdmin]", new String[]{"true"});

        params.put("user[address][3][state]", new String[]{"china"});
        params.put("user[address][3][city]", new String[]{"wuhan"});
        params.put("user[address][0][state]", new String[]{"china"});
        params.put("user[address][0][city]", new String[]{"shanghai"});
        
        params.put("user[mails][0]", new String[]{"dmx@qq.com"});
        params.put("user[mails][1]", new String[]{"dm.x@gmail.com"});
        params.put("user[mails][3]", new String[]{"xdm@aa.com"});
        params.put("user[father][name]", new String[]{"xdz"});
        params.put("user[father][age]", new String[]{"60"});
        
        return params;
    }
 
    public static void main(String[] args){
        System.out.println("a");
    }
    
}
