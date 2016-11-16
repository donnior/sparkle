package org.agilej.sparkle.core.argument;

import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.action.ActionMethodParameter;
import org.junit.Test;
import s.I;

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
            public boolean support(ActionMethodParameter actionMethodParameter) {
                return false;
            }

            @Override
            public Object resolve(ActionMethodParameter actionMethodParameter, WebRequest request) {
                return null;
            }
        };

        resolverList.add(resolver);

        registration.registerVendorArgumentResolvers(resolverList);
        assertTrue(7 == registration.getAllOrderedArgumentResolvers().size());
        assertSame(resolver, registration.getAllOrderedArgumentResolvers().get(6));


    }
}
