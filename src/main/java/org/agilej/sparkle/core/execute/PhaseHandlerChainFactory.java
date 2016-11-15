package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.core.action.ActionMethodResolver;
import org.agilej.sparkle.core.annotation.Singleton;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.agilej.sparkle.core.engine.CoreComponent;
import org.agilej.sparkle.core.method.ControllerInstanceResolver;
import org.agilej.sparkle.core.route.RouteBuilderResolver;
import org.agilej.sparkle.core.route.RouterImpl;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.interceptor.Interceptor;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Singleton
public class PhaseHandlerChainFactory {

    public PhaseHandlerChain phaseHandlerChain(CoreComponent component){

        InterceptorsPhaseHandler interceptorsHandler = interceptorsHandler(component.interceptors());
        RoutingPhaseHandler routingPhaseHandler      = routingPhaseHandler(component.routeBuilderResolver(), component.router());
        PathVariableResolvePhaseHandler pathVariableResolvePhaseHandler = pathVariableResolvePhaseHandler();
        ArgumentResolvePhaseHandler argumentResolvePhaseHandler = argumentResolvePhaseHandler(component.actionMethodResolver(), component.argumentResolverManager(),
                component.controllerInstanceResolver());
        FirstAttemptExecutePhaseHandler firstAttemptExecutePhaseHandler = firstAttemptExecutePhaseHandler();
        AsyncPhaseHandler asyncHandler = asyncHandler(component.asyncTaskExecutorService());
        ViewRenderPhaseHandler viewRenderPhaseHandler = viewRenderPhaseHandler(component.viewRenderResolver());
        EndLoopHandler endLoopHandler = endLoopHandler();

        AbstractPhaseHandler[] handlers = new AbstractPhaseHandler[]{
                interceptorsHandler, routingPhaseHandler, pathVariableResolvePhaseHandler,
                argumentResolvePhaseHandler, firstAttemptExecutePhaseHandler,
                asyncHandler, viewRenderPhaseHandler, endLoopHandler
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

    private RoutingPhaseHandler routingPhaseHandler(RouteBuilderResolver routeBuilderResolver, RouterImpl router){
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
                                                                    ArgumentResolverManager argumentResolverManager,
                                                                    ControllerInstanceResolver controllerInstanceResolver){

        ArgumentResolvePhaseHandler handler = new ArgumentResolvePhaseHandler();
        handler.setControllerInstanceResolver(controllerInstanceResolver);
        handler.setActionMethodResolver(actionMethodResolver);
        handler.setArgumentResolverManager(argumentResolverManager);
        return handler;
    }

    private FirstAttemptExecutePhaseHandler firstAttemptExecutePhaseHandler(){
        FirstAttemptExecutePhaseHandler firstAttemptExecutePhaseHandler = new FirstAttemptExecutePhaseHandler();
        return firstAttemptExecutePhaseHandler;
    }

    private AsyncPhaseHandler asyncHandler(ExecutorService executorService){
        AsyncPhaseHandler asyncHandler = new AsyncPhaseHandler();
        asyncHandler.setAsyncTaskExecutorService(executorService);
        return asyncHandler;
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
