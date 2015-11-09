package org.agilej.sparkle.core;


import java.util.concurrent.Callable;

public interface AsynchronousManager {

    boolean needAsyncProcess(ActionMethod actionMethod);

    boolean isAsyncResult(Object actionResult);

    void startAsyncProcess(Callable callable);

}
