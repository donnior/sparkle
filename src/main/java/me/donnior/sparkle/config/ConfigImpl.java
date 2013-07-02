package me.donnior.sparkle.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import me.donnior.fava.FList;
import me.donnior.fava.util.FLists;
import me.donnior.sparkle.view.ViewRender;

public class ConfigImpl implements Config, ConfigResult {

    private FList<Class<? extends ViewRender>> viewRenders = FLists.newEmptyList();
    private FList<String> controllerPackages = FLists.newEmptyList();
    
    @Override
    public void registerViewRenderClass(Class<? extends ViewRender> viewRenderClass) {
        if(!this.viewRenders.contains(viewRenderClass)){
            this.viewRenders.add(viewRenderClass);
        }
    }

    @Override
    public void registerControllerPackages(String... packages) {
        if(packages != null){
            this.controllerPackages.addAll(Arrays.asList(packages));
        }
    }
    
    @Override
    public FList<Class<? extends ViewRender>> getViewRenders() {
        return this.viewRenders.compact();
    }

    @Override
    public String[] getControllerPackages() {
        Set<String> set = new HashSet<String>(this.controllerPackages.compact());
        return set.toArray(new String[set.size()]);
    }

}
