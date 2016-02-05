package org.agilej.sparkle.core.engine;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.agilej.sparkle.core.request.LocaleResolver;
import org.agilej.sparkle.core.request.SessionStore;
import org.agilej.fava.FList;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.Env;
import org.agilej.sparkle.config.Config;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.core.view.JSONSerializerFactory;
import org.agilej.sparkle.core.view.ViewRender;
import org.agilej.sparkle.interceptor.Interceptor;
import org.agilej.sparkle.util.Singleton;

@Singleton
public class ConfigImpl implements Config, ConfigResult {

    //TODO change collection type to Set
    private FList<Class<? extends ViewRender>> viewRenders = FLists.newEmptyList();
    private FList<String> controllerPackages = FLists.newEmptyList();
    private FList<Interceptor> interceptors = FLists.newEmptyList();

    private Class<? extends SessionStore> sessionStoreClass;
    private Class<? extends ControllerFactory> controllerFactoryClass;
    private Class<? extends LocaleResolver> localeResolverClass;

    private ControllerFactory controllerFactory;

    private String basePackage = "";
    private String secretBase;
    private JSONSerializerFactory jsonViewSerializerFactory;

    @Override
    public Config registerViewRenderClass(Class<? extends ViewRender> viewRenderClass) {
        if(!this.viewRenders.contains(viewRenderClass)){
            this.viewRenders.add(viewRenderClass);
        }
        return this;
    }

    @Override
    public Config registerControllerPackages(String... packages) {
        if(packages != null){
            this.controllerPackages.addAll(Arrays.asList(packages));
        }
        return this;
    }

    @Override
    public Config registerBasePackage(String basePackage) {
        if(basePackage != null){
            this.basePackage = basePackage;
        }
        return this;
    }

    @Override
    public Config setMode(Env.Mode mode) {
        Env.setMode(mode);
        return this;
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
    public Config setControllerFactory(ControllerFactory controllerFactory) {
        this.controllerFactory = controllerFactory;
        return this;
    }

    @Override
    public Config setControllerFactoryClass(Class<? extends ControllerFactory> controllerFactoryClass) {
        this.controllerFactoryClass = controllerFactoryClass;
        return this;
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
    public Config registerInterceptor(Interceptor interceptor) {
        this.interceptors.add(interceptor);
        return this;
    }


    @Override
    public Config setSessionStoreClass(Class<? extends SessionStore> sessionStoreClass) {
        this.sessionStoreClass = sessionStoreClass;
        return this;
    }

    @Override
    public Class<? extends SessionStore> getSessionStoreClass() {
        return sessionStoreClass;
    }

    @Override
    public Config setSecretBase(String secretBase) {
        this.secretBase = secretBase;
        return this;
    }

    public String getSecretBase() {
        return secretBase;
    }

    @Override
    public Config setLocaleResolver(Class<? extends LocaleResolver> localeResolverClass) {
        this.localeResolverClass = localeResolverClass;
        return this;
    }

    @Override
    public Class<? extends LocaleResolver> getLocaleResolverClass() {
        return this.localeResolverClass;
    }

    @Override
    public JSONSerializerFactory jsonViewSerializerFactory() {
        return this.jsonViewSerializerFactory;
    }

    @Override
    public void setJsonViewSerializerFactory(JSONSerializerFactory jsonViewSerializerFactory) {
        this.jsonViewSerializerFactory = jsonViewSerializerFactory;
    }
}
