package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.core.action.ActionMethodResolver;
import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.core.annotation.Singleton;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.agilej.sparkle.core.engine.CoreComponent;
import org.agilej.sparkle.core.method.ControllerClassResolver;
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

        InterceptorsHandler interceptorsHandler = interceptorsHandler(component.interceptors());
        RoutingPhaseHandler routingPhaseHandler = routingPhaseHandler(component.routeBuilderResolver(), component.router());
        PathVariableResolvePhaseHandler pathVariableResolvePhaseHandler = pathVariableResolvePhaseHandler();
        FirstAttemptExecutePhaseHandler firstAttemptExecutePhaseHandler =
                firstAttemptExecutePhaseHandler(component.actionMethodResolver(), component.argumentResolverManager(),
                        component.controllerInstanceResolver());
        AsyncHandler asyncHandler = asyncHandler(component.asyncTaskExecutorService());
        ViewRenderPhaseHandler viewRenderPhaseHandler = viewRenderPhaseHandler(component.viewRenderResolver());
        EndLoopHandler endLoopHandler = endLoopHandler();


        interceptorsHandler.setNext(routingPhaseHandler);
        routingPhaseHandler.setNext(pathVariableResolvePhaseHandler);
        pathVariableResolvePhaseHandler.setNext(firstAttemptExecutePhaseHandler);
        firstAttemptExecutePhaseHandler.setNext(asyncHandler);
        asyncHandler.setNext(viewRenderPhaseHandler);
        viewRenderPhaseHandler.setNext(endLoopHandler);

        endLoopHandler.setPrevious(viewRenderPhaseHandler);
        viewRenderPhaseHandler.setPrevious(asyncHandler);
        asyncHandler.setPrevious(firstAttemptExecutePhaseHandler);
        firstAttemptExecutePhaseHandler.setPrevious(pathVariableResolvePhaseHandler);
        pathVariableResolvePhaseHandler.setPrevious(routingPhaseHandler);
        routingPhaseHandler.setPrevious(interceptorsHandler);


        return new PhaseHandlerChain(interceptorsHandler);
    }


    private InterceptorsHandler interceptorsHandler(List<Interceptor> interceptors){
        InterceptorsHandler interceptorsHandler = new InterceptorsHandler();
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

    private FirstAttemptExecutePhaseHandler firstAttemptExecutePhaseHandler(ActionMethodResolver actionMethodResolver,
                                                                            ArgumentResolverManager argumentResolverManager,
                                                                            ControllerInstanceResolver controllerInstanceResolver){
        FirstAttemptExecutePhaseHandler firstAttemptExecutePhaseHandler = new FirstAttemptExecutePhaseHandler();
        firstAttemptExecutePhaseHandler.setActionMethodResolver(actionMethodResolver);
        firstAttemptExecutePhaseHandler.setArgumentResolverManager(argumentResolverManager);
        firstAttemptExecutePhaseHandler.setControllerInstanceResolver(controllerInstanceResolver);
        return firstAttemptExecutePhaseHandler;
    }

    private AsyncHandler asyncHandler(ExecutorService executorService){
        AsyncHandler asyncHandler = new AsyncHandler();
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
