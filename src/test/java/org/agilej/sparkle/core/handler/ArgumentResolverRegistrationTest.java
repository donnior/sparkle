package org.agilej.sparkle.core.handler;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.ActionMethodArgument;
import org.agilej.sparkle.mvc.ArgumentResolver;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class ArgumentResolverRegistrationTest {

    @Test
    public void test_create(){
        ArgumentResolverRegistration registration = new ArgumentResolverRegistration();
        assertTrue(6 == registration.getAllOrderedArgumentResolvers().size());
    }

    @Test
    public void test_register_vender_argument_resolver(){
        ArgumentResolverRegistration registration = new ArgumentResolverRegistration();
        List<ArgumentResolver> resolverList = new ArrayList<>();

        ArgumentResolver resolver = new ArgumentResolver() {
            @Override
            public boolean support(ActionMethodArgument actionMethodParameter) {
                return false;
            }

            @Override
            public Object resolve(ActionMethodArgument actionMethodParameter, WebRequest request) {
                return null;
            }
        };

        resolverList.add(resolver);

        registration.registerVendorArgumentResolvers(resolverList);
        assertTrue(7 == registration.getAllOrderedArgumentResolvers().size());
        assertSame(resolver, registration.getAllOrderedArgumentResolvers().get(6));


    }
}
