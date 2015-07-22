package me.donnior.sparkle.core.request;

import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.interceptor.Interceptor;
import org.agilej.fava.FList;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CookieBasedSessionStoreTest {

    private CookieBasedSessionStore store;

    @Before
    public void setUp(){
        store = new CookieBasedSessionStore();
    }




}
