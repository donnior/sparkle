package me.donnior.sparkle.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.util.List;

import me.donnior.sparkle.annotation.Json;
import me.donnior.sparkle.annotation.Param;
import me.donnior.web.adapter.HttpServletRequestAdapter;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

public class DefaultParamResolversManagerTest {
    
    private DefaultParamResolversManager manager;
    
    @Before
    public void setup(){
        manager = new DefaultParamResolversManager();
    }
    
    @Test
    public void testDefaultConstructor(){
        assertEquals(1, manager.registeredResolvers().size());
        assertEquals(SimpleArgumentResolver.class, manager.registeredResolvers().get(0).getClass());
    }
    
    @Test (expected=RuntimeException.class)
    public void testCannotFindProperResovler(){
        
        List<Annotation> annotations = Lists.newArrayList();
        annotations.add(new Annotation() {
            
            @Override
            public Class<? extends Annotation> annotationType() {
                return Json.class;
            }
        });
        
        ActionParamDefinition actionParamDefinition = 
                new DefaulActionParamDefinition(String.class, annotations);
        
        manager.resolve(actionParamDefinition, null);
        fail();
    }

    @Test
    public void testResovleSucced(){
        
        ActionMethodDefinition actionParamDefinition = 
                new ActionMethodDefinitionFinder().find(ContrllerForDefaultParamResolversManagerTest.class, "index"); 
        assertEquals(1, actionParamDefinition.paramDefinitions().size());
               
                
        Object obj = manager.resolve(actionParamDefinition.paramDefinitions().get(0), new HttpServletRequestAdapter());
        assertNull(obj);
    }
    
    
}

class ContrllerForDefaultParamResolversManagerTest{
    
    @Json
    public String index(@Param("page") Integer page){
        return null;
    }
    
    public String show(){
        return null;
    }
    
    public String show(int id){
        return null;
    }
}