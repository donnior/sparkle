package org.agilej.sparkle.core.execute;

import org.agilej.sparkle.core.WebRequestExecutionContext;

public abstract class AbstractPhaseHandler implements PhaseHandler {

    private PhaseHandler next;
    private PhaseHandler previous;

    public void setNext(PhaseHandler next) {
        this.next = next;
    }

    public void setPrevious(PhaseHandler previous) {
        this.previous = previous;
    }

    @Override
    public PhaseHandler next() {
        return this.next;
    }

    @Override
    public PhaseHandler previous() {
        return this.previous;
    }

    @Override
    public void handle(WebRequestExecutionContext context) {

    }

    @Override
    public void postHandle(WebRequestExecutionContext context) {
        backwardToPrevious(context);
    }

    protected void forwardToNext(WebRequestExecutionContext context){
        if (this.next() != null) {
            next().handle(context);
        }
    }

    protected void backwardToPrevious(WebRequestExecutionContext context) {
        if (this.previous() != null) {
            previous().postHandle(context);
        }
    }

    /**
     * TODO make it abstract
     * @param context
     * @return indicator for continuing execute next handler
     */
    protected boolean doHandle(WebRequestExecutionContext context) {
        return false;
    }
}
