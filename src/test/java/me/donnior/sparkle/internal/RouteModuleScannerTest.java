package me.donnior.sparkle.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import me.donnior.fava.Predicate;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.data.module.ModuleOne;
import me.donnior.sparkle.route.RouteModule;

import org.junit.Test;

public class RouteModuleScannerTest {

    @Test
    public void testScanRoute(){
        String pkg = "me.donnior.sparkle.data.module";
        List<RouteModule> modules = new RouteModuleScanner().scanRouteModule(pkg);
        assertEquals(4, modules.size());
        
        boolean hasModuleOneClass = FLists.create(modules).any(new Predicate<RouteModule>() {
            
            @Override
            public boolean apply(RouteModule module) {
                return ModuleOne.class.equals(module.getClass());
            }
        });
        
        assertTrue(hasModuleOneClass);
    }

}
