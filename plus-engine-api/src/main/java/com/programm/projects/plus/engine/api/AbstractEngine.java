package com.programm.projects.plus.engine.api;

import com.programm.projects.core.lifecycle.AbstractObservableLifecycle;

public abstract class AbstractEngine extends AbstractObservableLifecycle implements IEngine {

    protected IRunLoop runLoop;

    protected abstract void update();

    @Override
    public void setRunLoop(IRunLoop runLoop) {
        this.runLoop = runLoop;
        addToChain(runLoop);
        runLoop.init(this::update);
    }


}
