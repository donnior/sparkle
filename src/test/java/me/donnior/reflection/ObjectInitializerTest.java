package me.donnior.reflection;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

public class ObjectInitializerTest {

    private ObjectInitializer initializer;
    
    @Before
    public void setup(){
        initializer = new ObjectInitializer();
    }
    
    @Test
    public void testSucceed(){
        Object obj = initializer.initialize(ClassWithPublicConstructor.class);
        assertNotNull(obj);
    }
    
    @Test(expected=RuntimeException.class)
    public void testFailed() throws Exception{
        initializer.initialize(ClassWithPrivateConstructor.class);
    }   

    @Test(expected=RuntimeException.class)
    public void testFailedTwo() throws Exception{
        initializer.initialize(ClassNeedParamsToConstruct.class);
    }   
    
}

class ClassWithPublicConstructor{

   
}


class ClassWithPrivateConstructor{

    private ClassWithPrivateConstructor(){}
    
}

class ClassNeedParamsToConstruct{

    public ClassNeedParamsToConstruct(int i){}
    
}
