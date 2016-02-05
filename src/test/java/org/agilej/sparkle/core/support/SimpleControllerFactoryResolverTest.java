package org.agilej.sparkle.core.support;


import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.support.EmptyConfig;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class SimpleControllerFactoryResolverTest {

    private SimpleControllerFactoryResolver resolver;

    @Before
    public void setUp() {
        resolver = new SimpleControllerFactoryResolver();
    }

    @Test
    public void test_default_controller_factory() {
        ControllerFactory controllerFactory = resolver.defaultControllerFactory();
        assertEquals(GuiceControllerFactory.class, controllerFactory.getClass());
    }

    @Test
    public void test_resolve_to_default_controller_factory() {
        ConfigResult config = new EmptyConfig();
        ControllerFactory controllerFactory = resolver.get(config);

        assertEquals(GuiceControllerFactory.class, controllerFactory.getClass());
    }

    @Test
    public void test_resolve_to_customize_controller_factory_instance() {

        ControllerFactory factory = new ControllerFactory() {
            @Override
            public Object get(String controllerName, Class<?> controllerClass) {
                return null;
            }
        };

        ConfigResult config = new EmptyConfig() {
            @Override
            public ControllerFactory getControllerFactory() {
                return factory;
            }
        };

        ControllerFactory controllerFactory = resolver.get(config);
        assertSame(factory, controllerFactory);

    }


    @Test
    public void test_resolve_to_customize_controller_factory_class() {

        ConfigResult config = new EmptyConfig() {
            @Override
            public Class<? extends ControllerFactory> getControllerFactoryClass() {
                return NullControllerFactory.class;
            }
        };

        ControllerFactory controllerFactory = resolver.get(config);
        assertEquals(NullControllerFactory.class, controllerFactory.getClass());
    }
}

