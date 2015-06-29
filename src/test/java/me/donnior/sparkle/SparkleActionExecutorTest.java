package me.donnior.sparkle;

import me.donnior.sparkle.core.ActionMethod;
import me.donnior.sparkle.core.resolver.ActionMethodFinder;
import me.donnior.sparkle.engine.ActionExecutor;

import org.junit.Before;
import org.junit.Test;

public class SparkleActionExecutorTest {

    private ActionExecutor executor;
    
    @Before
    public void setup(){
        this.executor = new ActionExecutor(null);
    }
    
    @Test
    public void testInvoke(){
        ActionMethod actionMethod =
                new ActionMethodFinder().find(ControllerForSparkleActionExecutor.class, "index");

        //FIXME
        //        Object result = executor.invoke(actionMethod, new ControllerForSparkleActionExecutor(),  new ServletWebRequest(mockRequest(), null));
        //        assertEquals("sparkle1", result);
    }
    
        
}


