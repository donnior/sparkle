package org.agilej.sparkle.core.engine;

import com.google.common.base.Stopwatch;
import org.agilej.reflection.ReflectionUtil;
import org.agilej.sparkle.HTTPMethod;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.config.Application;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.core.action.ActionMethodResolver;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.agilej.sparkle.core.config.ConfigResult;
import org.agilej.sparkle.core.engine.component.ArgumentResolverComponentInitializer;
import org.agilej.sparkle.core.engine.component.ControllerResolverComponentInitializer;
import org.agilej.sparkle.core.engine.component.RouterComponentInitializer;
import org.agilej.sparkle.core.engine.component.ViewRenderResolverComponentInitializer;
import org.agilej.sparkle.core.execute.PhaseHandlerChain;
import org.agilej.sparkle.core.execute.PhaseHandlerChainFactory;
import org.agilej.sparkle.core.ext.EnvSpecific;
import org.agilej.sparkle.core.method.*;
import org.agilej.sparkle.core.request.*;
import org.agilej.sparkle.core.route.*;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.interceptor.Interceptor;
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

    private ConfigImpl config;

    private RouteBuilderResolver routeBuilderResolver;
    private ControllerInstanceResolver controllerInstanceResolver;
    private ActionMethodResolver actionMethodResolver;
    private ViewRenderResolver viewRenderResolver;
    private ArgumentResolverManager argumentResolverManager;
    private ExecutorService asyncTaskExecutorService;

    private PhaseHandlerChain phaseHandlerChain;

    private EnvSpecific envSpecific;

    private final static Logger logger = LoggerFactory.getLogger(SparkleEngine.class);
    
    public SparkleEngine(EnvSpecific envSpecific){

        logger.info("Start initializing Sparkle framework.");

        Stopwatch stopwatch = Stopwatch.createStarted();

        this.envSpecific             = envSpecific;
        this.config                  = new ConfigImpl();
        this.interceptors            = new ArrayList<Interceptor>();

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
        initActionMethodResolverComponent(config);
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

    private void initActionMethodResolverComponent(ConfigResult config) {
        this.actionMethodResolver = new ActionMethodResolver();
    }

    private void initArgumentResolverComponent(ConfigResult config){
        this.argumentResolverManager =
                new ArgumentResolverComponentInitializer().initializeComponent(config, this.envSpecific);
    }
    private void initViewRenderComponent(ConfigResult config) {
        this.viewRenderResolver =
                new ViewRenderResolverComponentInitializer().initializeComponent(config, this.envSpecific);
    }

    private void initControllerResolverComponent(ConfigResult config) {
        this.controllerInstanceResolver =
                new ControllerResolverComponentInitializer().initializeComponent(config, this.envSpecific);
    }

    private void installRouterComponent() {
        this.routeBuilderResolver = new RouterComponentInitializer().initializeComponent(config, this.envSpecific);
    }

    private Application scanApplication() {
        Class<?> clz = new ApplicationConfigScanner().scan("");
        return clz != null ? (Application) ReflectionUtil.initialize(clz) : null;
    }

    public void doService(final WebRequest webRequest, HTTPMethod method){
        logger.info("Start process request : {} {}", webRequest.getMethod(), webRequest.getPath());

        WebRequestExecutionContext ctx = new WebRequestExecutionContext(webRequest);
        phaseHandlerChain().startPhaseHandle(ctx);
    }

    private PhaseHandlerChain phaseHandlerChain() {
        if (this.phaseHandlerChain == null) {
            this.phaseHandlerChain = new PhaseHandlerChainFactory().phaseHandlerChain(this);
        }
        return this.phaseHandlerChain;
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
    public RouteBuilderHolder router() {
        return this.routeBuilderResolver.routeBuilderHolder();
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
