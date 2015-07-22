package me.donnior.sparkle.core.request;

import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.support.EmptyConfig;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.exception.SparkleException;
import me.donnior.sparkle.interceptor.Interceptor;
import org.agilej.fava.FList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class SessionStoreResolverTest {

    private SessionStoreResolver resolver;

    @Before
    public void setUp() {
        resolver = new SessionStoreResolver();
    }

    @Test
    public void testDefaultSessionStore() {
        SessionStore sessionStore = resolver.defaultSessionStore();
        assertEquals(CookieBasedSessionStore.class, sessionStore.getClass());
    }

    @Test
    public void testResolveToDefaultSessionStore() {
        ConfigResult config = new EmptyConfig();
        SessionStore sessionStore = resolver.resolve(config);

        assertEquals(CookieBasedSessionStore.class, sessionStore.getClass());
    }

    @Test
    public void testResolveToCustomizeSessionStore() {
        ConfigResult config = new EmptyConfig() {
            @Override
            public Class<? extends SessionStore> getSessionStoreClass() {
                return SimpleMemorySessionStore.class;
            }
        };

        SessionStore sessionStore = resolver.resolve(config);

        assertEquals(SimpleMemorySessionStore.class, sessionStore.getClass());
    }
}
