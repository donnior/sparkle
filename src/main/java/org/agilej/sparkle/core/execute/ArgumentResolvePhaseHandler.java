package org.agilej.sparkle.core.execute;

import org.agilej.fava.Function;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.ApplicationController;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.mvc.ActionMethod;
import org.agilej.sparkle.mvc.ActionMethodArgument;
import org.agilej.sparkle.core.handler.ActionMethodResolver;
import org.agilej.sparkle.core.handler.ArgumentResolverResolver;
import org.agilej.sparkle.core.handler.ControllerResolver;
import org.agilej.sparkle.core.route.RouteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * phase for resolving controller instance and arguments
 */
public class ArgumentResolvePhaseHandler extends AbstractPhaseHandler {

    private ActionMethodResolver        actionMethodResolver;
    private ArgumentResolverResolver    argumentResolverResolver;
    private ControllerResolver          controllerInstanceResolver;

    private final static Logger logger = LoggerFactory.getLogger(ArgumentResolvePhaseHandler.class);

    @Override
    public void handle(WebRequestExecutionContext context) {
        RouteInfo rd = context.getRoute();

        if (!rd.isFunctionRoute()){
            WebRequest webRequest = context.webRequest();

            Object controller = resolveController(rd);
            presetControllerIfNeed(webRequest, controller);

            ActionMethod actionMethod = resolveActionMethod(rd, controller.getClass());

            Object[] arguments = resolvedArguments(actionMethod, context);

            context.setController(controller);
            context.setActionMethod(actionMethod);
            context.setArguments(arguments);
        }

        forwardToNext(context);
    }

    private ActionMethod resolveActionMethod(RouteInfo rd, Class controllerClass) {
        return this.actionMethodResolver.find(controllerClass, rd.getActionName());
    }

    private Object resolveController(RouteInfo routeInfo){
        return  this.controllerInstanceResolver.get(routeInfo.getControllerName());
    }

    private void presetControllerIfNeed(WebRequest webRequest, Object controller) {
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(webRequest);
            ((ApplicationController)controller).setResponse(webRequest.getWebResponse());
        }
    }

    private Object[] resolvedArguments(ActionMethod actionMethod, WebRequestExecutionContext context){
        List<ActionMethodArgument> amps = actionMethod.parameters();
        Object[] params = FLists.create(amps).collect(new Function<ActionMethodArgument, Object>() {
            public Object apply(ActionMethodArgument amp) {
                return argumentResolverResolver.resolve(amp).resolve(amp, context.webRequest());
            }
        }).toArray();
        return params;
    }

    public void setActionMethodResolver(ActionMethodResolver actionMethodResolver) {
        this.actionMethodResolver = actionMethodResolver;
    }

    public void setArgumentResolverResolver(ArgumentResolverResolver argumentResolverResolver) {
        this.argumentResolverResolver = argumentResolverResolver;
    }

    public void setControllerInstanceResolver(ControllerResolver controllerInstanceResolver) {
        this.controllerInstanceResolver = controllerInstanceResolver;
    }
}
