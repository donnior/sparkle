package me.donnior.sparkle.core.resolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.annotation.Json;
import me.donnior.sparkle.annotation.Param;
import me.donnior.sparkle.core.ActionMethodParamDefinition;
import me.donnior.web.adapter.GetWebRequest;

import org.junit.Test;

public class SimpleArgumentResolverTest {

    @Test
    public void test_support_ability(){
        SimpleArgumentResolver resolver = new SimpleArgumentResolver();
        
        ActionMethodParamDefinition apd = correctSupportedActionParamDefinition(String.class, "userName");
        
        assertTrue(resolver.support(apd));
        
        apd = createActionParamDefinition(String.class, Json.class, "userName");
        assertFalse(resolver.support(apd));
        
    }
    
    @Test
    public void test_null_value_with_paramname(){
        SimpleArgumentResolver resolver = new SimpleArgumentResolver();
        
        ActionMethodParamDefinition apd = correctSupportedActionParamDefinition(String.class, "userName");
        WebRequest request = new GetWebRequest(null){
            @Override
            public String[] getParameterValues(String paramName) {
                return null;
            }
        };
        
        Object result = resolver.resovle(apd, request);
        assertNull(result);

        apd = correctSupportedActionParamDefinition(int.class, "userName");
        
        try {
            result = resolver.resovle(apd, request);
            fail();
        } catch (RuntimeException re) {
            assertEquals("action method argument annotated with @Param not support primitive", re.getMessage());
        }

    }
    
    @Test
    public void test_normal_value_with_paramname(){
        SimpleArgumentResolver resolver = new SimpleArgumentResolver();
        
        ActionMethodParamDefinition apd = correctSupportedActionParamDefinition(Integer.class, "page");
        WebRequest request = new GetWebRequest(null){
            @Override
            public String[] getParameterValues(String paramName) {
                if(paramName.equals("page")){
                    return new String[]{"1"};
                }
                return super.getParameterValues(paramName);
            }
        };
        Object result = resolver.resovle(apd, request);
        
        assertEquals(Integer.valueOf(1), (Integer)result);
        
    }    
    

    private ActionMethodParamDefinition createActionParamDefinition(
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
        
        return new DefaulActionParamDefinition(paramType, Arrays.asList(an));
    }

    private ActionMethodParamDefinition correctSupportedActionParamDefinition(Class<?> paramType, final String annotationValue) {
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
        
        return new DefaulActionParamDefinition(paramType, Arrays.asList(userNameAnnotation));
    }

    
}
