package me.donnior.sparkle.core.support;


import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.support.EmptyConfig;
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
    public void testDefaultControllerFactory() {
        ControllerFactory controllerFactory = resolver.defaultControllerFactory();
        assertEquals(GuiceControllerFactory.class, controllerFactory.getClass());
    }

    @Test
    public void testResolveToDefaultControllerFactory() {
        ConfigResult config = new EmptyConfig();
        ControllerFactory controllerFactory = resolver.get(config);

        assertEquals(GuiceControllerFactory.class, controllerFactory.getClass());
    }

    @Test
    public void testResolveToCustomizeControllerFactoryInstance() {

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
    public void testResolveToCustomizeControllerFactoryClass() {

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

