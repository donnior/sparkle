package org.agilej.reflection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.junit.Test;

public class ReflectionUtilTest {

    @Test
    public void testInitializeSucceed(){
        Object obj = ReflectionUtil.initialize(ClassWithPublicConstructor.class);
        assertNotNull(obj);
    }
    
    @Test(expected=RuntimeException.class)
    public void testInitializeFailed() throws Exception{
        ReflectionUtil.initialize(ClassWithPrivateConstructor.class);
    }   

    @Test(expected=RuntimeException.class)
    public void testInitializeFailedTwo() throws Exception{
        ReflectionUtil.initialize(ClassNeedParamsToConstruct.class);
    }   
    
    
    @Test(expected=RuntimeException.class)
    public void testInvokeMethodFailedWithIllegalArgument(){
        Method mtd = null;
        try {
            mtd = ClassWithPublicConstructor.class.getMethod("index", new Class[]{});
        } catch (Exception e) {
            fail();
        }
        ReflectionUtil.invokeMethod(new ClassWithPublicConstructor(), mtd, new Object[]{1});
    }
   
}

class ClassWithPublicConstructor{

    public String index(){
        return null;
    }
   
}


class ClassWithPrivateConstructor{

    private ClassWithPrivateConstructor(){}
    
}

class ClassNeedParamsToConstruct{

    public ClassNeedParamsToConstruct(int i){}
    
}
