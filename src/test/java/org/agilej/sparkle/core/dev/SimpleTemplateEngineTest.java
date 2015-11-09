package org.agilej.sparkle.core.dev;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class SimpleTemplateEngineTest{

    @Test
    public void test() throws Exception{
        SimpleTemplateEngine engine = new SimpleTemplateEngine();

        String template = "hello @name , this is @what";

        Map<String, String> map = new HashMap<>();
        map.put("name", "java");
        map.put("what", "SimpleTemplateEngine");

        assertEquals("hello java, this is SimpleTemplateEngine", engine.render(template, map));
    }

}
