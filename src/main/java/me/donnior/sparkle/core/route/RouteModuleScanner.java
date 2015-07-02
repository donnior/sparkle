package me.donnior.sparkle.core.route;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import me.donnior.reflection.ReflectionUtil;
import me.donnior.sparkle.route.AbstractRouteModule;
import me.donnior.sparkle.route.RouteModule;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteModuleScanner {
    
    private final static Logger logger = LoggerFactory.getLogger(RouteModuleScanner.class);

    public List<RouteModule> scanRouteModule(String pkg) {
        List<RouteModule> routeModuleInstances = new ArrayList<RouteModule>();
        Reflections reflections = new Reflections("");
        Set<Class<? extends RouteModule>> fromInterface = reflections.getSubTypesOf(RouteModule.class);
        Set<Class<? extends AbstractRouteModule>> fromAbstracts = reflections.getSubTypesOf(AbstractRouteModule.class);
        fromInterface.addAll(fromAbstracts);
        for(Class<?> clz : fromInterface){
            if(Modifier.isAbstract(clz.getModifiers())){
                continue;
            }
            logger.debug("Found route module : {} ",clz.getName());
            routeModuleInstances.add((RouteModule)ReflectionUtil.initialize(clz));
        }
        return routeModuleInstances;
    }

}
