package me.donnior.sparkle.route;

import static org.junit.Assert.*;

import org.junit.Test;

public class RouteModuleTest {
    
    @Test
    public void testSuccess(){
        SampleRouteModule m = new SampleRouteModule();
        m.config(new RouterImpl());
        assertNull(m.router);
        
        assertNotNull(m.getResult);
        assertNotNull(m.postResult);
        assertNotNull(m.matchResult);
    }

    @Test
    public void testFail() {
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

class SampleRouteModule extends AbstractRouteModule{

    public HttpScoppedRoutingBuilder matchResult;
    public LinkedRoutingBuilder postResult;
    public LinkedRoutingBuilder getResult;
    
    @Override
    protected void configure() {
        postResult = post("/user/new");
        getResult = get("/user/1");
        matchResult = match("/user/donnior");
    }
    
}