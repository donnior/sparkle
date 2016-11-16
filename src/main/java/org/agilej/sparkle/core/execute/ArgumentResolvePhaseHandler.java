package org.agilej.sparkle.core.execute;

import org.agilej.fava.Function;
import org.agilej.fava.util.FLists;
import org.agilej.sparkle.ApplicationController;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.core.action.ActionMethod;
import org.agilej.sparkle.core.action.ActionMethodParameter;
import org.agilej.sparkle.core.action.ActionMethodResolver;
import org.agilej.sparkle.core.argument.ArgumentResolverResolver;
import org.agilej.sparkle.core.method.ControllerResolver;
import org.agilej.sparkle.core.route.RouteInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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

            ActionMethod actionMethod = resolveActionMethod(rd, controller);

            Object[] arguments = resolvedArguments(actionMethod, context);

            context.setController(controller);
            context.setActionMethod(actionMethod);
            context.setArguments(arguments);
        }

        forwardToNext(context);
    }

    private ActionMethod resolveActionMethod(RouteInfo rd, Object controller) {
        return this.actionMethodResolver.find(controller.getClass(), rd.getActionName());
    }

    private Object resolveController(RouteInfo routeInfo){
        return  this.controllerInstanceResolver.get(routeInfo);
    }

    private void presetControllerIfNeed(WebRequest webRequest, Object controller) {
        if(controller instanceof ApplicationController){
            ((ApplicationController)controller).setRequest(webRequest);
            ((ApplicationController)controller).setResponse(webRequest.getWebResponse());
        }
    }

    private Object[] resolvedArguments(ActionMethod actionMethod, WebRequestExecutionContext context){
        List<ActionMethodParameter> amps = actionMethod.parameters();
        Object[] params = FLists.create(amps).collect(new Function<ActionMethodParameter, Object>() {
            public Object apply(ActionMethodParameter amp) {
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
