package me.donnior.sparkle;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletRequest;

import me.donnior.sparkle.core.ActionMethodDefinition;
import me.donnior.sparkle.core.resolver.ActionMethodDefinitionFinder;
import me.donnior.sparkle.servlet.SparkleActionExecutor;
import me.donnior.web.adapter.HttpServletRequestAdapter;

import org.junit.Before;
import org.junit.Test;

public class SparkleActionExecutorTest {

    private SparkleActionExecutor executor;
    
    @Before
    public void setup(){
        this.executor = new SparkleActionExecutor();
    }
    
    @Test
    public void testInvoke(){
        ActionMethodDefinition adf = 
                new ActionMethodDefinitionFinder().find(ControllerForSparkleActionExecutor.class, "index");
        Object result = executor.invoke(adf, new ControllerForSparkleActionExecutor(), mockRequest(), null);
        assertEquals("sparkle1", result);
    }
    
    HttpServletRequest mockRequest(){
        return new HttpServletRequestAdapter(){
            @Override
            public String[] getParameterValues(String param) {
                
                if(param.equals("name")){
                    return new String[]{"sparkle"};
                }
                if(param.equals("page")){
                    return new String[]{"1"};
                }
                return null;
            }
        };
    }
    
}


