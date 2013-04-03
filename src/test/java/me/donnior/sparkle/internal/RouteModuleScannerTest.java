package me.donnior.sparkle.internal;

import static org.junit.Assert.*;

import java.util.List;

import me.donnior.sparkle.route.RouteModule;
import me.donnior.sparkle.route.Router;

import org.junit.Test;

public class RouteModuleScannerTest {

    @Test
    public void testScanRoute(){
        String pkg = "me.donnior.sparkle.internal";
        RouteModuleScanner scanner = new RouteModuleScanner();
        List<RouteModule> modules = scanner.scanRouteModule(pkg);
        assertEquals(1, modules.size());
        assertEquals(ModuleOne.class, modules.get(0).getClass());
    }
    
}
