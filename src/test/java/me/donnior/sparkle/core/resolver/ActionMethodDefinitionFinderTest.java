package me.donnior.sparkle.core.resolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import me.donnior.sparkle.annotation.Json;
import me.donnior.sparkle.annotation.Param;
import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.ActionMethodParamDefinition;

import me.donnior.sparkle.exception.SparkleException;
import org.junit.Test;

public class ActionMethodDefinitionFinderTest {
    
    @Test
    public void testFindActionMethodDefinitionSucceed(){
        ActionMethodDefinitionFinder finder = new ActionMethodDefinitionFinder();
        ActionMethodDefinition amd = finder.find(SampleContrllerClass.class, "index");
        
        assertEquals("index", amd.actionName());
        assertTrue(amd.hasAnnotation(Json.class));
        assertEquals(1, amd.annotations().size());
        assertEquals(Json.class, amd.annotations().get(0).annotationType());
        
        List<ActionMethodParamDefinition> apds = amd.paramDefinitions();
        assertEquals(2, apds.size());
        
        ActionMethodParamDefinition apd1 = apds.get(0);
        assertEquals(String.class, apd1.paramType());
        assertFalse(apd1.hasAnnotation(Param.class));
        assertEquals(0, apd1.annotations().size());
        try{
            apd1.paramName();
            fail();
        }catch(UnsupportedOperationException uoe){
            assertEquals("currently not allowed to get paramName", uoe.getMessage());
        }
        
        ActionMethodParamDefinition apd2 = apds.get(1);
        assertEquals(int.class, apd2.paramType());
        assertTrue(apd2.hasAnnotation(Param.class));
        assertFalse(apd2.hasAnnotation(Json.class));
        assertEquals(1, apd2.annotations().size());
        assertEquals("ActionParamDefinition:[type=>int]", apd2.toString());
        
    }

    @Test(expected = SparkleException.class)
    public void test_cannot_find_any_action_method(){
        ActionMethodDefinitionFinder finder = new ActionMethodDefinitionFinder();
        finder.find(SampleContrllerClass.class, "notExistMethod");
    }
    
    @Test(expected = SparkleException.class)
    public void test_find_more_than_one_action_method_with_same_name(){
        ActionMethodDefinitionFinder finder = new ActionMethodDefinitionFinder();
        finder.find(SampleContrllerClass.class, "show");
    }    

}


class SampleContrllerClass{
    
    @Json
    public String index(String type, @Param("page") int page){
        return null;
    }
    
    public String show(){
        return null;
    }
    
    public String show(int id){
        return null;
    }
}