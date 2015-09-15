package me.donnior.sparkle.core.route;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import me.donnior.sparkle.data.module.SampleRouteModule;

import org.junit.Test;

public class RouteModuleTest {
    
    @Test
    public void test_succeed(){
        SampleRouteModule m = new SampleRouteModule();
        m.config(new RouterImpl());
        assertNull(m.router);
        
        assertNotNull(m.getResult);
        assertNotNull(m.postResult);
        assertNotNull(m.matchResult);
    }

    @Test
    public void test_failure() {
        SampleRouteModule m = new SampleRouteModule();
        m.router = new RouterImpl();
        try{
            m.config(new RouterImpl());
            fail();
        }catch(IllegalStateException ise){}
        
        m = new SampleRouteModule();
        try{
            m.router();
            fail();
        }catch(IllegalStateException ise){}
    }

}

