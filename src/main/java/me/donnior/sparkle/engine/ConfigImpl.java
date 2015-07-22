package me.donnior.sparkle.engine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import me.donnior.sparkle.core.request.SessionStore;
import org.agilej.fava.FList;
import org.agilej.fava.util.FLists;
import me.donnior.sparkle.Environment;
import me.donnior.sparkle.Environment.Mode;
import me.donnior.sparkle.config.Config;
import me.donnior.sparkle.core.ConfigResult;
import me.donnior.sparkle.core.ControllerFactory;
import me.donnior.sparkle.core.view.ViewRender;
import me.donnior.sparkle.interceptor.Interceptor;
import me.donnior.sparkle.util.Singleton;

@Singleton
public class ConfigImpl implements Config, ConfigResult {

    //TODO change collection type to Set
    private FList<Class<? extends ViewRender>> viewRenders = FLists.newEmptyList();
    private FList<String> controllerPackages = FLists.newEmptyList();
    private String basePackage = "";
    private FList<Interceptor> interceptors = FLists.newEmptyList();
    private Class<? extends SessionStore> sessionStoreClass;
    private ControllerFactory controllerFactory;
    private Class<? extends ControllerFactory> controllerFactoryClass;

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
    public void registerBasePackage(String basePackage) {
        if(basePackage != null){
            this.basePackage = basePackage;
        }
    }

    @Override
    public void setMode(Mode mode) {
        Environment.setMode(mode);
    }
    
    @Override
    public FList<Class<? extends ViewRender>> getCustomizedViewRenders() {
        return this.viewRenders.compact();
    }

    @Override
    public String[] getControllerPackages() {
        Set<String> set = new HashSet<String>(this.controllerPackages.compact());
        return set.toArray(new String[set.size()]);
    }
    
    @Override
    public String getBasePackage() {
        return this.basePackage;
    }
    
    @Override
    public ControllerFactory getControllerFactory() {
        return this.controllerFactory;
    }

    @Override
    public void setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
    }

    @Override
    public void setControllerFactoryClass(Class<? extends ControllerFactory> controllerFactoryClass) {
        this.controllerFactoryClass = controllerFactoryClass;
    }

    @Override
    public Class<? extends ControllerFactory> getControllerFactoryClass() {
        return this.controllerFactoryClass;
    }
    
    @Override
    public FList<Interceptor> getInterceptors() {
        return this.interceptors ;
    }
    
    @Override
    public void registerInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
    }


    @Override
    public void setSessionStoreClass(Class<? extends SessionStore> sessionStoreClass) {
        this.sessionStoreClass = sessionStoreClass;
    }

    @Override
    public Class<? extends SessionStore> getSessionStoreClass() {
        return sessionStoreClass;
    }
}
