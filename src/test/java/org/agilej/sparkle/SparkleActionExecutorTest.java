package org.agilej.sparkle;

import org.agilej.sparkle.core.action.ActionMethod;
import org.agilej.sparkle.core.method.ActionMethodResolver;
import org.agilej.sparkle.core.method.ActionExecutor;

import org.junit.Before;
import org.junit.Test;

public class SparkleActionExecutorTest {

    private ActionExecutor executor;
    
    @Before
    public void setup(){
        this.executor = new ActionExecutor(null);
    }
    
    @Test
    public void test_invoke(){
        ActionMethod actionMethod =
                new ActionMethodResolver().find(ControllerForSparkleActionExecutor.class, "index");

        //FIXME
        //        Object result = executor.invoke(actionMethod, new ControllerForSparkleActionExecutor(),  new ServletWebRequest(mockRequest(), null));
        //        assertEquals("sparkle1", result);
    }
    
        
}


