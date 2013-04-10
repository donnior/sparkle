package me.donnior.sparkle.internal;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.route.RouteModule;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteModuleScanner {
    
    private final static Logger logger = LoggerFactory.getLogger(RouteModuleScanner.class);

    public List<RouteModule> scanRouteModule(String pkg) {
        List<RouteModule> routeModuleInstances = new ArrayList<RouteModule>();
        Reflections reflections = new Reflections(pkg);
        Set<Class<? extends RouteModule>> inherited = reflections.getSubTypesOf(RouteModule.class);
        for(Class<?> clz : inherited){
            if(Modifier.isAbstract(clz.getModifiers())){
                continue;
            }
            routeModuleInstances.add((RouteModule)ReflectionUtil.initialize(clz));
            logger.debug("created route module with class {} ",clz.getName());
            
        }
        return routeModuleInstances;
    }

}
