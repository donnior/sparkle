package org.agilej.sparkle.core.async;


import org.agilej.sparkle.core.action.ActionMethod;

import java.util.concurrent.Callable;

public interface AsynchronousManager {

    boolean needAsyncProcess(ActionMethod actionMethod);

    boolean isAsyncResult(Object actionResult);

    void startAsyncProcess(Callable callable);

}
