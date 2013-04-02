package me.donnior.sparkle.internal;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.donnior.sparkle.route.RouteModule;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteModuleScanner {
    
    private final static Logger logger = LoggerFactory.getLogger(RouteModuleScanner.class);

    public List<RouteModule> scanRouteModule() {
        List<RouteModule> routeModuleInstances = new ArrayList<RouteModule>();
        Reflections reflections = new Reflections("");
        Set<Class<? extends RouteModule>> inherited = reflections.getSubTypesOf(RouteModule.class);
        for(Class<?> clz : inherited){
            if(Modifier.isAbstract(clz.getModifiers())){
                continue;
            }
            try {
                routeModuleInstances.add((RouteModule)clz.newInstance());
                logger.debug("created route module with class {} ",clz.getName());
            } catch (InstantiationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
            
        }
        return routeModuleInstances;
    }

}
