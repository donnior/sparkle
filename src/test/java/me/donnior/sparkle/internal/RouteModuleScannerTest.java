package me.donnior.sparkle.internal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import me.donnior.sparkle.data.module.ModuleOne;
import me.donnior.sparkle.data.module.ModuleTwo;
import me.donnior.sparkle.data.module.SampleModule;
import me.donnior.sparkle.data.module.SampleRouteModule;
import me.donnior.sparkle.route.RouteModule;

import org.junit.Test;

public class RouteModuleScannerTest {

    @Test
    public void testScanRoute(){
        String pkg = "me.donnior.sparkle.data.module";
        List<RouteModule> modules = new RouteModuleScanner().scanRouteModule(pkg);
        assertEquals(4, modules.size());
//        assertTrue(modules.contains(ModuleOne.class));
//        assertTrue(modules.contains(ModuleTwo.class));
//        assertTrue(modules.contains(SampleModule.class));
//        assertTrue(modules.contains(SampleRouteModule.class));
    }

}
