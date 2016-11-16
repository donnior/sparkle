package org.agilej.sparkle.core.engine;

import org.agilej.sparkle.core.action.ActionMethodResolver;
import org.agilej.sparkle.core.argument.ArgumentResolverResolver;
import org.agilej.sparkle.core.method.ControllerResolver;
import org.agilej.sparkle.core.route.RouteBuilderHolder;
import org.agilej.sparkle.core.route.RouteBuilderResolver;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.interceptor.Interceptor;

import java.util.List;
import java.util.concurrent.ExecutorService;

public interface CoreComponent {

    List<Interceptor> interceptors();

    RouteBuilderResolver routeBuilderResolver();

    RouteBuilderHolder router();

    ActionMethodResolver actionMethodResolver();

    ArgumentResolverResolver argumentResolverResolver();

    ViewRenderResolver viewRenderResolver();

    ControllerResolver controllerInstanceResolver();

    ExecutorService asyncTaskExecutorService();

}
