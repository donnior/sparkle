package org.agilej.sparkle.core.execute;

import com.google.common.base.Stopwatch;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.mvc.ActionMethod;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.agilej.sparkle.mvc.ViewRender;
import org.agilej.sparkle.core.view.ViewRenderResolver;
import org.agilej.sparkle.exception.SparkleException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ViewRenderPhaseHandler extends AbstractPhaseHandler {

    private ViewRenderResolver viewRenderResolver;

    private final static Logger logger = LoggerFactory.getLogger(ViewRenderPhaseHandler.class);

    @Override
    public void handle(WebRequestExecutionContext context) {
        Stopwatch stopwatch = context.stopwatch().reset().start();
        long actionTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        boolean needRenderView = true; // need render view or proceed manually
        if (needRenderView) {
            processViewResult(context.getActionResult(), context, context.getController(), context.getActionMethod());
        } else {
            logger.debug("Http mvc response has been proceed manually, ignore view rendering.");
        }

        forwardToNext(context);
    }

    private void processViewResult(Object result, WebRequestExecutionContext ctx,
                                   Object controller, ActionMethod actionMethod){
        Stopwatch stopwatch = ctx.stopwatch().stop();
        long actionTime = stopwatch.elapsed(TimeUnit.MILLISECONDS);

        stopwatch.reset().start();


        doRenderViewPhase(ctx.webRequest(), controller, actionMethod, result);

        long viewTime = stopwatch.stop().elapsed(TimeUnit.MILLISECONDS);
        logger.info("Completed request within {} ms (Action: {} ms | View: {} ms)\n",
                new Object[]{viewTime + actionTime, actionTime, viewTime });
    }


    public void doRenderViewPhase(WebRequest webRequest, Object controller, ActionMethod actionMethod, Object result) {
        if (controller != null && actionMethod != null){
            logger.debug("Render view for {}#{} with result type : {}",
                    controller.getClass().getSimpleName(), actionMethod.actionName(), result.getClass().getSimpleName());
        } else {  // it's a functional route
            logger.debug("Render view for functional route with result type : {}", result.getClass().getSimpleName());
        }

        ViewRender viewRender = this.viewRenderResolver.resolveViewRender(actionMethod, result);
        if(viewRender != null){
            try {
                viewRender.renderView(result, controller, webRequest);
            } catch (IOException e) {
                throw new SparkleException(e.getMessage());
            }
        } else {
            logger.error("Could not find any view render for request {}, result type is {}",
                    webRequest, result.getClass().getSimpleName());
        }
    }

    private boolean isResponseProcessedManually(ActionMethod actionMethod) {
//        return this.envSpecific.getLifeCycleManager().isResponseProcessedManually(actionMethod);
        //TODO
        return false;
    }

    public void setViewRenderResolver(ViewRenderResolver viewRenderResolver) {
        this.viewRenderResolver = viewRenderResolver;
    }
}
