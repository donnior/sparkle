package me.donnior.sparkle.interceptor;


import me.donnior.fava.util.FLists;
import me.donnior.sparkle.engine.InterceptorExecutionChain;
import me.donnior.web.adapter.GetWebRequest;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InterceptorExecutionChainTest {

    @Test
    public void testNormal(){
        SuccessInterceptor interceptorOne = new SuccessInterceptor();
        InterceptorExecutionChain iec = new InterceptorExecutionChain(FLists.$(interceptorOne));
        iec.doPreHandle(new GetWebRequest("/url"));
        assertTrue(interceptorOne.isExecuted());
    }

    @Test
    public void testInterceptorsWithOneFail(){
        SuccessInterceptor interceptorOne = new SuccessInterceptor();
        FailInterceptor interceptorTwo = new FailInterceptor();
        SuccessInterceptor interceptorThree = new SuccessInterceptor();

        InterceptorExecutionChain iec = new InterceptorExecutionChain(
                FLists.$(interceptorOne, interceptorTwo, interceptorThree));
        iec.doPreHandle(new GetWebRequest("/url"));

        assertTrue(interceptorOne.isExecuted());
        assertTrue(interceptorTwo.isExecuted());
        assertFalse(interceptorThree.isExecuted());

        iec.doAfterHandle(new GetWebRequest("/url"));
        assertTrue(interceptorOne.isCleaned());
        assertTrue(interceptorTwo.isCleaned());
        assertFalse(interceptorThree.isCleaned());
    }

    @Test
    public void testInterceptorsWithOneException(){
        SuccessInterceptor interceptorOne = new SuccessInterceptor();
        ExceptionInterceptor interceptorTwo = new ExceptionInterceptor();
        SuccessInterceptor interceptorThree = new SuccessInterceptor();

        InterceptorExecutionChain iec = new InterceptorExecutionChain(
                FLists.$(interceptorOne, interceptorTwo, interceptorThree));
        try {
            iec.doPreHandle(new GetWebRequest("/url"));
        } catch (Exception ex){
            assertTrue(interceptorOne.isExecuted());
            assertTrue(interceptorTwo.isExecuted());
            assertFalse(interceptorThree.isExecuted());
        }

        iec.doAfterHandle(new GetWebRequest("/url"));
        assertTrue(interceptorOne.isCleaned());
        assertTrue(interceptorTwo.isCleaned());
        assertFalse(interceptorThree.isCleaned());
    }

}
