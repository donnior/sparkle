package org.agilej.sparkle.core.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.annotation.Json;
import org.agilej.sparkle.annotation.Param;
import org.agilej.sparkle.mvc.ActionMethodParameter;
import org.agilej.sparkle.core.handler.ParamAnnotationArgumentResolver;
import org.agilej.sparkle.core.handler.DefaultActionMethodParameter;
import org.agilej.web.adapter.GetWebRequest;

import org.junit.Test;

public class SimpleArgumentResolverTest {

    @Test
    public void test_support_ability(){
        ParamAnnotationArgumentResolver resolver = new ParamAnnotationArgumentResolver();
        
        ActionMethodParameter apd = correctSupportedActionParamDefinition(String.class, "userName");
        
        assertTrue(resolver.support(apd));
        
        apd = createActionParamDefinition(String.class, Json.class, "userName");
        assertFalse(resolver.support(apd));
    }
    
    @Test
    public void test_null_value_with_paramname(){
        ParamAnnotationArgumentResolver resolver = new ParamAnnotationArgumentResolver();
        
        ActionMethodParameter apd = correctSupportedActionParamDefinition(String.class, "userName");
        WebRequest request = new GetWebRequest(null){
            @Override
            public String[] getParameterValues(String paramName) {
                return null;
            }
        };
        
        Object result = resolver.resolve(apd, request);
        assertNull(result);

        apd = correctSupportedActionParamDefinition(int.class, "userName");
        
        try {
            result = resolver.resolve(apd, request);
            fail();
        } catch (RuntimeException re) {
            assertEquals("handler method handler annotated with @Param not support primitive", re.getMessage());
        }
    }
    
    @Test
    public void test_normal_value_with_paramname(){
        ParamAnnotationArgumentResolver resolver = new ParamAnnotationArgumentResolver();
        
        ActionMethodParameter apd = correctSupportedActionParamDefinition(Integer.class, "page");
        WebRequest request = new GetWebRequest(null){
            @Override
            public String[] getParameterValues(String paramName) {
                if(paramName.equals("page")){
                    return new String[]{"1"};
                }
                return super.getParameterValues(paramName);
            }
        };
        Object result = resolver.resolve(apd, request);
        
        assertEquals(Integer.valueOf(1), (Integer)result);
    }

    private ActionMethodParameter createActionParamDefinition(
            final Class<?> paramType, final Class<? extends Annotation> annotationType, final String annotationValue) {
        Annotation an = new Annotation() {
            
            @Override
            public Class<? extends Annotation> annotationType() {
                return annotationType;
            }
            
            public String value() {
                return annotationValue;
            }
        };
        
        return new DefaultActionMethodParameter(paramType, Arrays.asList(an));
    }

    private ActionMethodParameter correctSupportedActionParamDefinition(Class<?> paramType, final String annotationValue) {
        Annotation userNameAnnotation = new Param() {
            
            @Override
            public Class<? extends Annotation> annotationType() {
                return Param.class;
            }
            
            @Override
            public String value() {
                return annotationValue;
            }
        };
        
        return new DefaultActionMethodParameter(paramType, Arrays.asList(userNameAnnotation));
    }

    
}