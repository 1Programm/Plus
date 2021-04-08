package com.programm.projects.plus.engine.simple;

import com.programm.projects.plus.engine.api.AbstractEngine;
import com.programm.projects.plus.engine.api.EnginePhase;
import com.programm.projects.plus.engine.api.IRunLoop;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.goh.simple.SimpleListGOH;
import com.programm.projects.plus.renderer.api.IRenderer;
import com.programm.projects.plus.renderer.swing.SwingRenderer;
import com.programm.projects.plus.resource.api.IResourceManager;
import com.programm.projects.plus.resources.simple.SimpleResourceLoader;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleEngine extends AbstractEngine {

    private IResourceManager resourceLoader;
    private IRunLoop tmpRunLoop;
    private IRenderer tmpRenderer;
    private IGameObjectHandler tmpGOH;

    public SimpleEngine() {
        this.resourceLoader = new SimpleResourceLoader();
        this.tmpRunLoop = new SimpleRunLoop(60);
        this.tmpRenderer = new SwingRenderer();
        this.tmpGOH = new SimpleListGOH();
    }

    @Override
    protected IResourceManager initResourceManager() {
        return resourceLoader;
    }

    @Override
    protected IRunLoop initRunLoop() {
        return tmpRunLoop;
    }

    @Override
    protected IGameObjectHandler initGOH() {
        return tmpGOH;
    }

    @Override
    protected IRenderer initRenderer() {
        return tmpRenderer;
    }

    /*
     * GETTER AND SETTER
     * Can only be used before starting the engine
     */

    private boolean testPhase(){
        if(phase() != EnginePhase.ALIVE){
            log.error("Cannot set Subsystems after engine started!");
            return true;
        }

        return false;
    }

    public void setResourceLoader(IResourceManager resourceLoader){
        if(testPhase()) return;
        this.resourceLoader = resourceLoader;
    }

    public void setRunLoop(IRunLoop runLoop) {
        if(testPhase()) return;
        this.runLoop = runLoop;
    }

    public void setGOH(IGameObjectHandler goh) {
        if(testPhase()) return;
        this.goh = goh;
    }

    public void setRenderer(IRenderer renderer) {
        if(testPhase()) return;
        this.renderer = renderer;
    }
}
