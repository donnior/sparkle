package me.donnior.sparkle.core.request;

import me.donnior.sparkle.Session;
import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.exception.SparkleException;
import me.donnior.web.adapter.WebRequestAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionStoreHolderTest {

    @Before
    public void setUp(){
        SessionStoreHolder.reset();
    }

    @Test(expected = SparkleException.class)
    public void testGetWhenNotSet(){
        SessionStoreHolder.get();
    }

    @Test
    public void testGet(){
        SessionStore sessionStore = new SimpleMemorySessionStore();
        SessionStoreHolder.set(sessionStore);
        assertSame(sessionStore, SessionStoreHolder.get());

        SessionStore sessionStore2 = new CookieBasedSessionStore();
        SessionStoreHolder.set(sessionStore2);
        assertSame(sessionStore2, SessionStoreHolder.get());
    }

}
