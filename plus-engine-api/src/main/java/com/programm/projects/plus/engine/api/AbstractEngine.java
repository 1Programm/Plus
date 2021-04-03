package com.programm.projects.plus.engine.api;

import com.programm.projects.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.plus.goh.api.IGameObjectHandler;


public abstract class AbstractEngine extends AbstractObservableLifecycle implements IEngine {

    private final EngineContext context = new EngineContext();

    protected IRunLoop runLoop;
    protected IGameObjectHandler goh;

    protected void update(){
        goh.update(context);
    }

    @Override
    public void setRunLoop(IRunLoop runLoop) {
        if(this.runLoop != null){
            removeLifecycle(this.runLoop);
        }

        this.runLoop = runLoop;
        addLifecycle(runLoop);
        runLoop.init(this::update);
    }

    @Override
    public void setGOH(IGameObjectHandler goh) {
        if(this.goh != null){
            removeLifecycle(this.goh);
        }

        this.goh = goh;
        addLifecycle(goh);
        //goh.init();
    }

    @Override
    public IGameObjectHandler getGOH() {
        return goh;
    }
}
