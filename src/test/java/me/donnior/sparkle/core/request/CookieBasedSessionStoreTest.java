package me.donnior.sparkle.core.request;

import me.donnior.sparkle.WebRequest;
import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.interceptor.Interceptor;
import me.donnior.web.adapter.WebRequestAdapter;
import org.agilej.fava.FList;
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
    public void testSetSession(){
        WebRequest request = new WebRequestAdapter();
        store.set(request, "name", "value");

    }


}
