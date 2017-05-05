package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.core.handler.ActionMethodResolver;
import org.agilej.sparkle.core.annotation.Singleton;
import org.agilej.sparkle.core.handler.ArgumentResolverResolver;
import org.agilej.sparkle.core.CoreComponent;
import org.agilej.sparkle.core.handler.ControllerResolver;
import org.agilej.sparkle.core.route.RouteBuilderHolder;
import org.agilej.sparkle.core.route.RouteBuilderResolver;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.Interceptor;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Singleton
public class PhaseHandlerChainFactory {

    public PhaseHandlerChain phaseHandlerChain(CoreComponent component){

        AbstractPhaseHandler interceptorsHandler    = interceptorsHandler(component.interceptors());
        AbstractPhaseHandler routingPhaseHandler    = routingPhaseHandler(component.routeBuilderResolver(), component.router());
        AbstractPhaseHandler pathVariableResolvePhaseHandler = pathVariableResolvePhaseHandler();
        AbstractPhaseHandler argumentResolvePhaseHandler  =
                argumentResolvePhaseHandler(component.actionMethodResolver(), component.argumentResolverResolver(),
                        component.controllerInstanceResolver());
        AbstractPhaseHandler syncExecutePhaseHandler    = syncExecutePhaseHandler();
        AbstractPhaseHandler asyncExecutePhaseHandler   = asyncExecutePhaseHandler(component.asyncTaskExecutorService());
        AbstractPhaseHandler viewRenderPhaseHandler     = viewRenderPhaseHandler(component.viewRenderResolver());
        AbstractPhaseHandler endLoopHandler             = endLoopHandler();

        AbstractPhaseHandler[] handlers = new AbstractPhaseHandler[]{
                interceptorsHandler, routingPhaseHandler, pathVariableResolvePhaseHandler,
                argumentResolvePhaseHandler, syncExecutePhaseHandler,
                asyncExecutePhaseHandler, viewRenderPhaseHandler, endLoopHandler
        };

        for (int i = 0; i < handlers.length - 1; i++) {
            handlers[i].setNext(handlers[i+1]);
        }

        for (int i = handlers.length - 1; i > 0; i--) {
            handlers[i].setPrevious(handlers[i-1]);
        }

        return new PhaseHandlerChain(interceptorsHandler);
    }


    private InterceptorsPhaseHandler interceptorsHandler(List<Interceptor> interceptors){
        InterceptorsPhaseHandler interceptorsHandler = new InterceptorsPhaseHandler();
        interceptorsHandler.setInterceptors(interceptors);
        return interceptorsHandler;
    }

    private RoutingPhaseHandler routingPhaseHandler(RouteBuilderResolver routeBuilderResolver, RouteBuilderHolder router){
        RoutingPhaseHandler routingPhaseHandler = new RoutingPhaseHandler();
        routingPhaseHandler.setRouteBuilderResolver(routeBuilderResolver);
        routingPhaseHandler.setRouter(router);
        return routingPhaseHandler;
    }

    private PathVariableResolvePhaseHandler pathVariableResolvePhaseHandler(){
        PathVariableResolvePhaseHandler pathVariableResolvePhaseHandler = new PathVariableResolvePhaseHandler();
        return  pathVariableResolvePhaseHandler;
    }

    private ArgumentResolvePhaseHandler argumentResolvePhaseHandler(ActionMethodResolver actionMethodResolver,
                                                                    ArgumentResolverResolver argumentResolverManager,
                                                                    ControllerResolver controllerInstanceResolver){
        ArgumentResolvePhaseHandler handler = new ArgumentResolvePhaseHandler();
        handler.setControllerInstanceResolver(controllerInstanceResolver);
        handler.setActionMethodResolver(actionMethodResolver);
        handler.setArgumentResolverResolver(argumentResolverManager);
        return handler;
    }

    private SyncExecutePhaseHandler syncExecutePhaseHandler(){
        SyncExecutePhaseHandler syncExecutePhaseHandler = new SyncExecutePhaseHandler();
        return syncExecutePhaseHandler;
    }

    private AsyncExecutePhaseHandler asyncExecutePhaseHandler(ExecutorService executorService){
        AsyncExecutePhaseHandler asyncExecutePhaseHandler = new AsyncExecutePhaseHandler();
        asyncExecutePhaseHandler.setAsyncTaskExecutorService(executorService);
        return asyncExecutePhaseHandler;
    }

    private ViewRenderPhaseHandler viewRenderPhaseHandler(ViewRenderResolver viewRenderResolver) {
        ViewRenderPhaseHandler viewRenderPhaseHandler = new ViewRenderPhaseHandler();
        viewRenderPhaseHandler.setViewRenderResolver(viewRenderResolver);
        return viewRenderPhaseHandler;
    }

    private EndLoopHandler endLoopHandler(){
        return new EndLoopHandler();
    }
}
