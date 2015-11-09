package org.agilej.sparkle.core.resolver;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.agilej.fava.Predicate;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.core.route.RouteModuleScanner;
import org.agilej.sparkle.data.module.ModuleOne;
import org.agilej.sparkle.route.RouteModule;

import org.junit.Test;

public class RouteModuleScannerTest {

    @Test
    public void test_scan_route(){
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
