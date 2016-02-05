package org.agilej.sparkle.interceptor;


import org.agilej.fava.util.FLists;
import org.agilej.sparkle.core.engine.InterceptorExecutionChain;
import org.agilej.web.adapter.GetWebRequest;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class InterceptorExecutionChainTest {

    @Test
    public void test_normal(){
        SuccessInterceptor interceptorOne = new SuccessInterceptor();
        InterceptorExecutionChain iec = new InterceptorExecutionChain(FLists.$(interceptorOne));
        iec.doPreHandle(new GetWebRequest("/url"));
        assertTrue(interceptorOne.isExecuted());
    }

    @Test
    public void test_interceptors_with_one_fail(){
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
    public void test_interceptors_with_one_exception(){
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

    @Test
    public void test_empty_interceptors() {
        InterceptorExecutionChain iec = new InterceptorExecutionChain(new ArrayList<>());
        iec.doPreHandle(new GetWebRequest("/url"));
        assertTrue(iec.isAllPassed());
    }

}
