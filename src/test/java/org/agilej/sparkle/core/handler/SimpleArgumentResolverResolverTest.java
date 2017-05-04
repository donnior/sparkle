package org.agilej.sparkle.core.handler;

import org.agilej.sparkle.Params;
import org.agilej.sparkle.mvc.ActionMethodArgument;
import org.agilej.sparkle.exception.SparkleException;
import org.agilej.sparkle.mvc.ArgumentResolver;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SimpleArgumentResolverResolverTest {

    @Test
    public void test_simple_argument_resolver_resolver_create(){
        ArgumentResolverRegistration registration = new ArgumentResolverRegistration();
        SimpleArgumentResolverResolver resolver = new SimpleArgumentResolverResolver(registration);

        assertEquals(6, resolver.registeredResolvers().size());
    }

    @Test
    public void test_get_match_resolver_succeed(){
        ArgumentResolverRegistration registration = new ArgumentResolverRegistration();
        SimpleArgumentResolverResolver resolver = new SimpleArgumentResolverResolver(registration);

        ArgumentResolver argumentResolver = resolver.resolve(new ActionMethodArgument() {
            @Override
            public String paramName() {
                return "params";
            }

            @Override
            public Class<?> paramType() {
                return Params.class;
            }

            @Override
            public List<Annotation> annotations() {
                return null;
            }

            @Override
            public boolean hasAnnotation(Class<?> annotationType) {
                return false;
            }

            @Override
            public Annotation getAnnotation(Class<?> annotationType) {
                return null;
            }
        });

        assertEquals(ParamsArgumentResolver.class, argumentResolver.getClass());
    }

    @Test(expected = SparkleException.class)
    public void test_get_match_resolver_exception(){
        SimpleArgumentResolverResolver resolver = new SimpleArgumentResolverResolver();
        resolver.resolve(new ActionMethodArgument() {
            @Override
            public String paramName() {
                return "p";
            }

            @Override
            public Class<?> paramType() {
                return Thread.class;
            }

            @Override
            public List<Annotation> annotations() {
                return null;
            }

            @Override
            public boolean hasAnnotation(Class<?> annotationType) {
                return false;
            }

            @Override
            public Annotation getAnnotation(Class<?> annotationType) {
                return null;
            }
        });
    }
}
