package org.agilej.sparkle.core;

import org.agilej.sparkle.core.handler.ActionMethodResolver;
import org.agilej.sparkle.core.handler.ArgumentResolverResolver;
import org.agilej.sparkle.core.handler.ControllerResolver;
import org.agilej.sparkle.core.route.RouteBuilderHolder;
import org.agilej.sparkle.core.route.RouteBuilderResolver;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.Interceptor;

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
