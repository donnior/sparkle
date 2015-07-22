package me.donnior.sparkle.core.request;

import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
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
    public void setUp(){
        resolver = new SessionStoreResolver();
    }

    @Test
    public void testDefaultSessionStore(){
        SessionStore sessionStore = resolver.defaultSessionStore();
        assertEquals(CookieBasedSessionStore.class, sessionStore.getClass());
    }

    @Test
    public void testResolveToDefaultSessionStore(){
        ConfigResult config = new EmpetyConfig();
        SessionStore sessionStore = resolver.resolve(config);

        assertEquals(CookieBasedSessionStore.class, sessionStore.getClass());
    }

    @Test
    public void testResolveToCustomizeSessionStore(){
        ConfigResult config = new EmpetyConfig(){
            @Override
            public Class<? extends SessionStore> getSessionStoreClass() {
                return SimpleMemorySessionStore.class;
            }
        };

        SessionStore sessionStore = resolver.resolve(config);

        assertEquals(SimpleMemorySessionStore.class, sessionStore.getClass());
    }

    static class EmpetyConfig implements ConfigResult{

        @Override
        public String[] getControllerPackages() {
            return new String[0];
        }

        @Override
        public FList<Class<? extends ViewRender>> getCustomizedViewRenders() {
            return null;
        }

        @Override
        public String getBasePackage() {
            return null;
        }

        @Override
        public ControllerFactory getControllerFactory() {
            return null;
        }

        @Override
        public Class<? extends ControllerFactory> getControllerFactoryClass() {
            return null;
        }

        @Override
        public FList<Interceptor> getInterceptors() {
            return null;
        }

        @Override
        public Class<? extends SessionStore> getSessionStoreClass() {
            return null;
        }
    }

}
