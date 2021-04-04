package com.programm.projects.plus.engine.api;

import com.programm.projects.core.lifecycle.AbstractObservableLifecycle;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.renderer.api.IRenderer;


public abstract class AbstractEngine extends AbstractObservableLifecycle implements IEngine {

    private final EngineContext context = new EngineContext();

    protected IRunLoop runLoop;
    protected IGameObjectHandler goh;
    protected IRenderer renderer;

    protected void update(){
        //Update all objects
        goh.update(context);

        //Render scene
        renderer.update(context);
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
    public void setRenderer(IRenderer renderer) {
        if(this.renderer != null){
            removeLifecycle(this.renderer);
        }

        this.renderer = renderer;
        addLifecycle(renderer);
        //renderer.init();
    }

    @Override
    public IGameObjectHandler getGOH() {
        return goh;
    }
}
