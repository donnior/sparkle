package me.donnior.sparkle.core.route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import me.donnior.sparkle.data.module.SampleModule;
import me.donnior.sparkle.route.RouteModule;

import org.junit.Test;

import static org.junit.Assert.*;

public class RouterImplTest {
    
    @Test
    public void testConstructor(){
        
        RouterImpl impl1 = new RouterImpl();
        RouterImpl impl2 = new RouterImpl();
        
        assertNotSame(impl1, impl2);
        
    }
    
    
    @Test
    public void testMatch(){
        RouterImpl router = new RouterImpl();
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
            rrbs.add(new RouteBuilder("/user"));
            fail();
        }catch(UnsupportedOperationException uoe){}
        
    }
    
    @Test
    public void testInstallModule(){
        
        RouterImpl router = new RouterImpl();
        
        Collection<RouteModule> modules = new ArrayList<RouteModule>();
        modules.add(new SampleModule());
        
        router.install(modules);
        
        List<RouteBuilder> rrbs = router.getRegisteredRouteBuilders();
        assertEquals(1, rrbs.size());
       
    }
    
}

