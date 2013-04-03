package me.donnior.sparkle.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import me.donnior.sparkle.annotation.Json;
import me.donnior.sparkle.annotation.Param;

import org.junit.Test;

public class ActionMethodDefinitionFinderTest {
    
    @Test
    public void testFindActionMethodDefinitionSucceed(){
        ActionMethodDefinitionFinder finder = new ActionMethodDefinitionFinder();
        ActionMethodDefinition amd = finder.find(SampleContrllerClass.class, "index");
        
        assertEquals("index", amd.actionName());
        assertTrue(amd.hasAnnotation(Json.class));
        assertEquals(1, amd.annotions().size());
        assertEquals(Json.class, amd.annotions().get(0).annotationType());
        
        List<ActionParamDefinition> apds = amd.paramDefinitions();
        assertEquals(2, apds.size());
        
        ActionParamDefinition apd1 = apds.get(0);
        assertEquals(String.class, apd1.paramType());
        assertFalse(apd1.hasAnnotation(Param.class));
        assertEquals(0, apd1.annotions().size());
        try{
            apd1.paramName();
            fail();
        }catch(UnsupportedOperationException uoe){
            assertEquals("currently not allowed to get paramName", uoe.getMessage());
        }
        
        ActionParamDefinition apd2 = apds.get(1);
        assertEquals(int.class, apd2.paramType());
        assertTrue(apd2.hasAnnotation(Param.class));
        assertFalse(apd2.hasAnnotation(Json.class));
        assertEquals(1, apd2.annotions().size());
        assertEquals("ActionParamDefinition:[type=>int]", apd2.toString());
        
    }
    
    @Test
    public void test_cannot_find_any_action_method(){
        ActionMethodDefinitionFinder finder = new ActionMethodDefinitionFinder();
        try{
            finder.find(SampleContrllerClass.class, "notExistMethod");
            fail();
        }catch(RuntimeException re){
            String expectedMessage = "can't find any action matched notExistMethod";
            assertEquals(expectedMessage, re.getMessage());
        }
        
    }
    
    @Test
    public void test_find_more_than_one_action_method_with_same_name(){
        ActionMethodDefinitionFinder finder = new ActionMethodDefinitionFinder();
        try{
            finder.find(SampleContrllerClass.class, "show");
            fail();
        }catch(RuntimeException re){
            String expectedMessage = "find more than actions with same name show";
            assertEquals(expectedMessage, re.getMessage());
        }
        
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