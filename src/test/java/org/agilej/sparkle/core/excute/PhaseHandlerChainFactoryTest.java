package org.agilej.sparkle.core.excute;

import org.agilej.sparkle.core.action.ActionMethodResolver;
import org.agilej.sparkle.core.argument.ArgumentResolverResolver;
import org.agilej.sparkle.core.engine.CoreComponent;
import org.agilej.sparkle.core.execute.*;
import org.agilej.sparkle.core.method.ControllerResolver;
import org.agilej.sparkle.core.route.RouteBuilderResolver;
import org.agilej.sparkle.core.route.RouterImpl;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.interceptor.Interceptor;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutorService;

import static org.junit.Assert.assertEquals;

public class PhaseHandlerChainFactoryTest {

    @Test
    public void test() throws Exception{
        PhaseHandlerChainFactory factory = new PhaseHandlerChainFactory();

        PhaseHandlerChain chain = factory.phaseHandlerChain(component());

        PhaseHandler handler = chain.firstHandler();
        assertEquals(InterceptorsPhaseHandler.class, handler.getClass());

        handler = handler.next();
        assertEquals(RoutingPhaseHandler.class, handler.getClass());

        handler = handler.next();
        assertEquals(PathVariableResolvePhaseHandler.class, handler.getClass());

        handler = handler.next();
        assertEquals(ArgumentResolvePhaseHandler.class, handler.getClass());

        handler = handler.next();
        assertEquals(SyncExecutePhaseHandler.class, handler.getClass());

        handler = handler.next();
        assertEquals(AsyncExecutePhaseHandler.class, handler.getClass());

        handler = handler.next();
        assertEquals(ViewRenderPhaseHandler.class, handler.getClass());

        handler = handler.next();
        assertEquals(EndLoopHandler.class, handler.getClass());

    }

    private CoreComponent component(){
        return new CoreComponent() {
            @Override
            public List<Interceptor> interceptors() {
                return null;
            }

            @Override
            public RouteBuilderResolver routeBuilderResolver() {
                return null;
            }

            @Override
            public RouterImpl router() {
                return null;
            }

            @Override
            public ActionMethodResolver actionMethodResolver() {
                return null;
            }

            @Override
            public ArgumentResolverResolver argumentResolverResolver() {
                return null;
            }

            @Override
            public ViewRenderResolver viewRenderResolver() {
                return null;
            }

            @Override
            public ControllerResolver controllerInstanceResolver() {
                return null;
            }

            @Override
            public ExecutorService asyncTaskExecutorService() {
                return null;
            }
        };
    }


}
