package org.agilej.sparkle.core.request;

import org.agilej.sparkle.Session;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.SessionStore;
import org.agilej.web.adapter.WebRequestAdapter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class AbstractWebRequestTest {

    private WebRequestAdapter request;

    @Before
    public void setUp(){
        request = new WebRequestAdapter();
    }

    @Test(expected = RuntimeException.class)
    public void test_get_session_id(){
        request.getSessionId();
    }

    @Test
    public void test_get_session(){
        SessionStoreHolder.set(new CookieBasedSessionStore());
        Session session = request.session();
        assertTrue(SessionFacade.class.equals(session.getClass()));

        SessionFacade sf = (SessionFacade)session;
        SessionStore store = sf.sessionStore();
        WebRequest webRequest = sf.webRequest();

        assertEquals(store.getClass(), CookieBasedSessionStore.class);
        assertSame(webRequest, request);
    }

}
