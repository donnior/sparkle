package org.agilej.sparkle.core.engine;

import com.google.common.base.Stopwatch;
import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.HTTPMethod;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.config.Application;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.core.action.ActionMethodResolver;
import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.core.action.SimpleControllerFactoryResolver;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.agilej.sparkle.core.argument.ArgumentResolverRegistration;
import org.agilej.sparkle.core.argument.SimpleArgumentResolverManager;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.execute.PhaseHandlerChain;
import org.agilej.sparkle.core.execute.PhaseHandlerChainFactory;
import org.agilej.sparkle.core.ext.EnvSpecific;
import org.agilej.sparkle.core.ext.VendorArgumentResolverProvider;
import org.agilej.sparkle.core.ext.VendorViewRenderProvider;
import org.agilej.sparkle.core.method.*;
import org.agilej.sparkle.core.request.*;
import org.agilej.sparkle.core.route.*;
import org.agilej.sparkle.core.view.SimpleViewRenderResolver;
import org.agilej.sparkle.core.view.ViewRenderRegistration;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.interceptor.Interceptor;
import org.agilej.sparkle.route.RouteModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SparkleEngine implements CoreComponent{

    private List<Interceptor> interceptors;

    private RouterImpl router;
    private ConfigImpl config;

    private ControllerFactory controllerFactory;
    private RouteBuilderResolver routeBuilderResolver;

    private ControllerInstanceResolver controllerInstanceResolver;
    private ActionMethodResolver actionMethodResolver;
    private ViewRenderResolver viewRenderResolver;
    private ArgumentResolverManager argumentResolverManager;
    private ExecutorService asyncTaskExecutorService;

    private EnvSpecific envSpecific;

    private final static Logger logger = LoggerFactory.getLogger(SparkleEngine.class);
    
    public SparkleEngine(EnvSpecific envSpecific){

        logger.info("Start initializing Sparkle framework.");

        Stopwatch stopwatch = Stopwatch.createStarted();

        this.envSpecific             = envSpecific;
        this.config                  = new ConfigImpl();
        this.interceptors            = new ArrayList<Interceptor>();

        this.router                  = new RouterImpl();
        this.routeBuilderResolver    = new SimpleRouteBuilderResolver(this.router);

        this.controllerFactory       = new SimpleControllerFactoryResolver().get(this.config);

        this.actionMethodResolver    = new ActionMethodResolver();

//        this.argumentResolverManager = this.envSpecific.getArgumentResolverManager();

        this.asyncTaskExecutorService = Executors.newCachedThreadPool(); //.newFixedThreadPool(100);

        this.startup();

        stopwatch.stop();
        logger.info("Sparkle framework start succeed within {} ms \n", stopwatch.elapsed(TimeUnit.MILLISECONDS));
    }

    protected void startup() {
        Application application = scanApplication();
        if(application != null){
            logger.info("Found customized application config : {}", application.getClass().getSimpleName());
            application.config(config);
        }else{
            logger.info("Could not find any application config, Sparkle will use the default configuration");
        }
        initEngineWithConfig(config);
    }
 
    private void initEngineWithConfig(ConfigResult config) {
        initViewRenderComponent(config);
        initArgumentResolverComponent(config);
        initControllerResolverComponent(config);
        installRouterComponent();
        initInterceptorComponent(config);
        initSessionStoreComponent(config);
        initLocaleResolverComponent(config);
    }

    private void initLocaleResolverComponent(ConfigResult config) {
        LocaleResolver localeResolver = new LocaleResolverResolver(config).resolve();
        LocaleResolverHolder.set(localeResolver);
        logger.info("Sparkle's locale resolver is configured to: {}", localeResolver.getClass().getSimpleName());
    }

    private void initSessionStoreComponent(ConfigResult config) {
        SessionStore sessionStore = new SessionStoreResolver(config).resolve();
        SessionStoreHolder.set(sessionStore);
        logger.info("Sparkle's session store is configured to: {}", sessionStore.getClass().getSimpleName());
    }

    private void initInterceptorComponent(ConfigResult config) {
        this.interceptors.addAll(config.getInterceptors());
    }

    private void initArgumentResolverComponent(ConfigResult config){
        ArgumentResolverRegistration registration = new ArgumentResolverRegistration();
        registration.registerAppScopedArgumentResolver(config.getCustomizedArgumentResolvers());

        VendorArgumentResolverProvider vendorArgumentResolverProvider = this.envSpecific.vendorArgumentResolverProvider();
        if (vendorArgumentResolverProvider != null) {
            registration.registerVendorArgumentResolvers(vendorArgumentResolverProvider.vendorArgumentResolvers());
        }

        SimpleArgumentResolverManager argumentResolverManager = new SimpleArgumentResolverManager();
        argumentResolverManager.registerArgumentResolvers(registration.getAllOrderedArgumentResolvers());

        this.argumentResolverManager = argumentResolverManager;

    }
    private void initViewRenderComponent(ConfigResult config) {
        ViewRenderRegistration viewRenderRegistration = new ViewRenderRegistration();
        viewRenderRegistration.registerAppScopedViewRender(config.getCustomizedViewRenders());

        VendorViewRenderProvider vendorViewRenderProvider = this.envSpecific.vendorViewRenderProvider();
        if (vendorViewRenderProvider != null){
            viewRenderRegistration.registerVendorViewRenders(vendorViewRenderProvider.vendorViewRenders());
        }
        //TODO remove ViewRenderManager, inject Config to SimpleViewRenderResolver directly and resolve all renders
        this.viewRenderResolver = new SimpleViewRenderResolver(viewRenderRegistration.getAllOrderedViewRenders());
    }

    private void initControllerResolverComponent(ConfigResult config) {
        //TODO how to deal with multi controller packages
        Map<String, Class<?>> scannedControllers = new ControllerClassScanner().scanControllers(this.config.getBasePackage());

        ControllerClassResolver controllerClassResolver = new ControllersHolder();
        controllerClassResolver.registerControllers(scannedControllers, true);

        SimpleControllerInstanceResolver simpleControllerInstanceResolver = new SimpleControllerInstanceResolver();
        simpleControllerInstanceResolver.setControllerFactory(this.controllerFactory);
        simpleControllerInstanceResolver.setControllerClassResolver(controllerClassResolver);
        this.controllerInstanceResolver = simpleControllerInstanceResolver;
    }

    private void installRouterComponent() {
        String routePackage = "";
        List<RouteModule> routeModules = new RouteModuleScanner().scanRouteModule(routePackage);
        this.router.install(routeModules);
        for(RouteBuilder rb : this.router.getRegisteredRouteBuilders()){
            logger.info("Registered route : {}", rb.toString());
        }
    }

    private Application scanApplication() {
        Class<?> clz = new ApplicationConfigScanner().scan("");
        return clz != null ? (Application) ReflectionUtil.initialize(clz) : null;
    }

    public void doService(final WebRequest webRequest, HTTPMethod method){
        logger.info("Start process request : {} {}", webRequest.getMethod(), webRequest.getPath());

        WebRequestExecutionContext ctx      = new WebRequestExecutionContext(webRequest);
        PhaseHandlerChain phaseHandlerChain = new PhaseHandlerChainFactory().phaseHandlerChain(this);

        phaseHandlerChain.startPhaseHandle(ctx);
    }

    public void shutdown(){
        asyncTaskExecutorService.shutdown();
    }

    @Override
    public List<Interceptor> interceptors() {
        return this.interceptors;
    }

    @Override
    public RouteBuilderResolver routeBuilderResolver() {
        return this.routeBuilderResolver;
    }

    @Override
    public RouterImpl router() {
        return this.router;
    }

    @Override
    public ActionMethodResolver actionMethodResolver() {
        return this.actionMethodResolver;
    }

    @Override
    public ArgumentResolverManager argumentResolverManager() {
        return this.argumentResolverManager;
    }

    @Override
    public ControllerInstanceResolver controllerInstanceResolver() {
        return this.controllerInstanceResolver;
    }

    @Override
    public ExecutorService asyncTaskExecutorService() {
        return this.asyncTaskExecutorService;
    }

    @Override
    public ViewRenderResolver viewRenderResolver() {
        return this.viewRenderResolver;
    }
}
