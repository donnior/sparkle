package org.agilej.sparkle.core.engine;

import org.agilej.sparkle.core.action.ActionMethodResolver;
import org.agilej.sparkle.core.action.ControllerFactory;
import org.agilej.sparkle.core.argument.ArgumentResolver;
import org.agilej.sparkle.core.argument.ArgumentResolverManager;
import org.agilej.sparkle.core.method.ControllerClassResolver;
import org.agilej.sparkle.core.method.ControllerInstanceResolver;
import org.agilej.sparkle.core.route.RouteBuilderResolver;
import org.agilej.sparkle.core.route.RouterImpl;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.interceptor.Interceptor;

import java.util.List;
import java.util.concurrent.ExecutorService;

public interface CoreComponent {

    List<Interceptor> interceptors();

    RouteBuilderResolver routeBuilderResolver();

    RouterImpl router();

    ActionMethodResolver actionMethodResolver();

    ArgumentResolverManager argumentResolverManager();

    ViewRenderResolver viewRenderResolver();

    ControllerInstanceResolver controllerInstanceResolver();

    ExecutorService asyncTaskExecutorService();

}
