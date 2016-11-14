package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.Env;
import org.agilej.sparkle.WebRequest;
import org.agilej.sparkle.async.DeferredResult;
import org.agilej.sparkle.core.action.ActionMethod;
import org.agilej.sparkle.core.dev.ExceptionHandler;
import org.agilej.sparkle.core.WebRequestExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncHandler extends AbstractPhaseHandler {

    private ExecutorService asyncTaskExecutorService;

    private final static Logger logger = LoggerFactory.getLogger(AsyncHandler.class);

    @Override
    public void handle(WebRequestExecutionContext context) {
        WebRequest webRequest = context.webRequest();
        Object tempResult     = context.getControllerActionResult();

        if(tempResult instanceof Callable){
            handleCallableResult(context, (Callable<Object>)tempResult);
            return;
        }

        if (tempResult instanceof DeferredResult) {
            handleDeferredResult(context, tempResult);
            return;
        }

        //not async result, go to render phase
        forwardToNext(context);
    }

    private void handleCallableResult(WebRequestExecutionContext context, Callable<Object> tempResult) {
        logger.debug("Execute action asynchronously for {}#{}",
                context.getController().getClass().getSimpleName(),
                context.getActionMethod().actionName());

        context.webRequest().startAsync();
        startAsyncProcess((Callable<Object>)tempResult, context,
                result->{
                    forwardToNext(context);
                    context.webRequest().completeAsync();
                },
                error -> {
                    logger.error("Error occurred while executing asynchronously action : {}", error);

                    context.webRequest().getWebResponse().setStatus(500);
                    if (Env.isDev()){
                        new ExceptionHandler(error).handle(context.webRequest());
                    }
                    this.postHandle(context);
                    context.webRequest().completeAsync();
                }
        );
    }

    private void handleDeferredResult(WebRequestExecutionContext context, Object tempResult) {
        logger.debug("Execute action asynchronously for {}#{}",
                context.getController().getClass().getSimpleName(),
                context.getActionMethod().actionName());

        context.webRequest().startAsync();
        ((DeferredResult) tempResult).setResultHandler(new DeferredResult.DeferredResultHandler() {
            @Override
            public void handleResult(Object result) {
                forwardToNext(context);
                context.webRequest().completeAsync();
            }
        });
    }



    private void startAsyncProcess(final Callable<Object> callable,
                                   final WebRequestExecutionContext ctx,
                                   final Consumer succeedConsumer,
                                   final Consumer<Throwable> errorConsumer){

        CompletableFuture
                .supplyAsync(() -> {
                    try {
                        return callable.call();
                    } catch (Exception e) {
                        throw new RuntimeException("Execute async action failed with : ", e);
                    }
                }, asyncTaskExecutorService)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        succeedConsumer.accept(result);
                    } else {
                        errorConsumer.accept(ex.getCause());
                    }
                });  //TODO deal the order completeAsync and execute after filters
    }

    public void setAsyncTaskExecutorService(ExecutorService asyncTaskExecutorService) {
        this.asyncTaskExecutorService = asyncTaskExecutorService;
    }
}
