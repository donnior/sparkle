package org.agilej.sparkle.core.request;

import org.agilej.sparkle.WebRequest;
import org.agilej.web.adapter.WebRequestAdapter;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CookieBasedSessionStoreTest {

    private CookieBasedSessionStore store;

    @Before
    public void setUp(){
        store = new CookieBasedSessionStore();
    }

    @Test
    @Ignore
    public void test_set_session(){
        WebRequest request = new WebRequestAdapter();
        store.set(request, "name", "value");

    }


}
