package org.agilej.sparkle.core.request;

import org.agilej.sparkle.exception.SparkleException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SessionStoreHolderTest {

    @Before
    public void setUp(){
        SessionStoreHolder.reset();
    }

    @Test(expected = SparkleException.class)
    public void test_get_when_not_set(){
        SessionStoreHolder.get();
    }

    @Test
    public void test_get(){
        SessionStore sessionStore = new SimpleMemorySessionStore();
        SessionStoreHolder.set(sessionStore);
        assertSame(sessionStore, SessionStoreHolder.get());

        SessionStore sessionStore2 = new CookieBasedSessionStore();
        SessionStoreHolder.set(sessionStore2);
        assertSame(sessionStore2, SessionStoreHolder.get());
    }

}
