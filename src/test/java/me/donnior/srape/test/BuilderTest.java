package me.donnior.srape.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import me.donnior.srape.AbstractFieldExposerModule;
import me.donnior.srape.FieldExposerModule;
import me.donnior.srape.JSONBuilder;

import org.junit.Test;

import com.google.common.collect.Lists;

public class BuilderTest {

    @Test
//    @Ignore
    public void testPrimaryTypes(){
        final int age = 12;
        final short s = 1;
        final long l = 1l;
        final String name = "srape";
        final float f = 1.23f;
        final double d = 1.25d;
        
        FieldExposerModule module = new AbstractFieldExposerModule() {
            public void config() {
                expose(age).withName("int");        //int
                expose('a').withName("char");       //char
                expose((byte)1).withName("byte");   //byte
                expose(s).withName("short");        //short
                expose(l).withName("long");         //long
                expose(f).withName("float");        //float
                expose(d).withName("double");       //double
                expose(name).withName("login");     //string
            }
        };
        
        //should be {"int":12,"char":"a","byte":1,"short":1,"long":1,"float":1.23,"double":1.25,"login":"srape"}
        String expected = "{\"int\":12,\"char\":\"a\",\"byte\":1,\"short\":1,\"long\":1,\"float\":1.23,\"double\":1.25,\"login\":\"srape\"}";
        assertEquals(expected, build(module));
        System.out.println(build(module));
    }
    
    
    @Test
//    @Ignore
    public void testArrayWithPrimaryTypes(){
        
        FieldExposerModule module = new AbstractFieldExposerModule() {
            public void config() {
                expose(new int[]{1,2,3}).withName("ints");        //ints
                expose(new String[]{"one","two","three2"}).withName("strings");        //strings
                
            }
        };
        
        String expected = "{\"ints\":[1,2,3],\"strings\":[\"one\",\"two\",\"three2\"]}";
        assertEquals(expected, build(module));
        System.out.println(build(module));
    }
    
    @Test
//    @Ignore
    public void testCollectionWithPrimaryTypes(){
        
        FieldExposerModule module = new AbstractFieldExposerModule() {
            public void config() {
                expose(Lists.newArrayList(1,2,3)).withName("ints");        //ints
                expose(Lists.newArrayList("one","two","three")).withName("strings");        //strings
                
            }
        };
        
        String expected = "{\"ints\":[1,2,3],\"strings\":[\"one\",\"two\",\"three\"]}";
        assertEquals(expected, build(module));
        System.out.println(build(module));
    }

    @Test
    public void testMapWithPrimaryTypes(){
        
        final Map<String, Object> map = new LinkedHashMap<String, Object>();
        map.put("name", "jam\"es");
        map.put("age", 18);
        
        FieldExposerModule module = new AbstractFieldExposerModule() {
            public void config() {
                expose(map).withName("map");        //map
            }
        };
        
        String expected = "{\"map\":{\"name\":\"jam\\\"es\",\"age\":18}}";
        assertEquals(expected, build(module));
        System.out.println(build(module));
    }
    
    @Test
//  @Ignore
  public void testOnlyOneCollectionDataWithName(){
      FieldExposerModule module = new AbstractFieldExposerModule() {
          public void config() {
              expose(Lists.newArrayList(1,2,3)).withName(null);        //ints
          }
      };
      
      String exptected = "{\"\":[1,2,3]}";
      assertEquals(exptected, build(module));
      System.out.println(build(module));
  }
    
    @Test
//  @Ignore
  public void testOnlyOneCollectionDataWithoutName(){
      FieldExposerModule module = new AbstractFieldExposerModule() {
          public void config() {
              expose(Lists.newArrayList(1,-2,3));
          }
      };
      
      String exptected = "[1,-2,3]";
      assertEquals(exptected, build(module));
      System.out.println(build(module));
  }
    
    @Test
//  @Ignore
  public void test_null_value(){
      FieldExposerModule module = new AbstractFieldExposerModule() {
          public void config() {
              expose(null).withName("null");
          }
      };
      
      String exptected = "{\"null\":null}";
      assertEquals(exptected, build(module));
      System.out.println(build(module));
  }
    
    @Test
//  @Ignore
  public void test_bool_value(){
      FieldExposerModule module = new AbstractFieldExposerModule() {
          public void config() {
              expose(true).withName("bool");
          }
      };
      
      String exptected = "{\"bool\":true}";
      assertEquals(exptected, build(module));
      System.out.println(build(module));
  }

    private String build(FieldExposerModule module){
        return new JSONBuilder(module).build();
    }
    
}