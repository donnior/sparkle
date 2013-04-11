package me.donnior.sparkle.route;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;

public class RouterImplTest {
    
    @Test
    public void testConstructor(){
        
        RouterImpl impl1 = RouterImpl.getInstance();
        RouterImpl impl2 = RouterImpl.getInstance();
        
        assertSame(impl1, impl2);
        
    }
    
    
    @Test
    public void testMatch(){
        RouterImpl router = RouterImpl.getInstance();
        router.clear();
        
        assertEquals(0, router.getRegisteredRouteBuilders().size());
        
        router.match("/a");
        assertEquals(1, router.getRegisteredRouteBuilders().size());
        
        router.match("/b");
        assertEquals(2, router.getRegisteredRouteBuilders().size());
        
        router.match("/a");
        assertEquals(3, router.getRegisteredRouteBuilders().size());
       
    }

    @Test
    public void testGetRegisteredRouteBuilders(){
        RouterImpl router = new RouterImpl();
        
        
        List<RouteBuilder> rrbs = router.getRegisteredRouteBuilders();
        
        assertEquals(0, rrbs.size());
        
        try{
            rrbs.add(new RouteBuilder());
            fail();
        }catch(UnsupportedOperationException uoe){}
        
    }
    
    @Test
    public void testInstallModule(){
        class SampleModule extends AbstractRouteModule{

            @Override
            protected void configure() {
                post("/user");
            }
            
        }
        RouterImpl router = new RouterImpl();
        
        Collection<RouteModule> modules = new ArrayList<RouteModule>();
        modules.add(new SampleModule());
        
        router.install(modules);
        
        List<RouteBuilder> rrbs = router.getRegisteredRouteBuilders();
        assertEquals(1, rrbs.size());
       
    }
    
}

