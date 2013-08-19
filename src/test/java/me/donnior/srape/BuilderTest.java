package me.donnior.srape;

import java.util.HashMap;
import java.util.Map;

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
        
        System.out.println(build(module));
    }

    @Test
    public void testMapWithPrimaryTypes(){
        
        final Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "jam\"es");
        map.put("age", 18);
        
        FieldExposerModule module = new AbstractFieldExposerModule() {
            public void config() {
                expose(map).withName("map");        //map
            }
        };
        
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
      
      System.out.println(build(module));
  }

    private String build(FieldExposerModule module){
        return new JSONBuilder(module).build();
    }
    
}