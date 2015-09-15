package me.donnior.sparkle.core.request;

import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.engine.ConfigImpl;
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

    @Test
    public void test_resolve_to_cookie_session_store_failed() {
        ConfigResult config = new EmptyConfig(){};

        SessionStoreResolver resolver = new SessionStoreResolver(config);
        SessionStore sessionStore = resolver.resolve();
    }

    @Test
    public void test_default_session_store() {
        SessionStoreResolver resolver = new SessionStoreResolver(new ConfigImpl(){
            @Override
            public String getSecretBase() {
                return "123";
            }
        });
        SessionStore sessionStore = resolver.defaultSessionStore();
        assertEquals(CookieBasedSessionStore.class, sessionStore.getClass());
        assertEquals("123", ((CookieBasedSessionStore)sessionStore).getSecret());
    }

    @Test
    public void test_resolve_to_default_session_store() {
        ConfigResult config = new EmptyConfig(){
            @Override
            public Class<? extends SessionStore> getSessionStoreClass() {
                return CookieBasedSessionStore.class;
            }

            @Override
            public String getSecretBase() {
                return "123";
            }
        };

        SessionStoreResolver resolver = new SessionStoreResolver(config);
        SessionStore sessionStore = resolver.resolve();
        assertEquals(CookieBasedSessionStore.class, sessionStore.getClass());
        assertEquals("123", ((CookieBasedSessionStore)sessionStore).getSecret());
    }

    @Test
    public void test_resolve_to_customize_session_store() {
        ConfigResult config = new EmptyConfig() {
            @Override
            public Class<? extends SessionStore> getSessionStoreClass() {
                return SimpleMemorySessionStore.class;
            }
        };

        SessionStoreResolver resolver = new SessionStoreResolver(config);
        SessionStore sessionStore = resolver.resolve();
        assertEquals(SimpleMemorySessionStore.class, sessionStore.getClass());
    }
}
