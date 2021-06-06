package com.programm.projects.plus.engine.simple;

import com.programm.projects.plus.collision.api.ICollisionHandler;
import com.programm.projects.plus.collision.simple.SimpleCollisionHandler;
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
    private ICollisionHandler tmpCollisionHandler;

    public SimpleEngine() {
        this.resourceLoader = new SimpleResourceLoader();
        this.tmpRunLoop = new SimpleRunLoop();
        this.tmpRenderer = new SwingRenderer();
        this.tmpGOH = new SimpleListGOH();
        this.tmpCollisionHandler = new SimpleCollisionHandler();
    }

    @Override
    protected void init(IResourceManager resourceManager) {
        resourceManager.addStaticResource("/engine-default.properties");
        resourceManager.addStaticResource("/game-default.properties");
        resourceManager.addStaticResource("/engine.xml");
        resourceManager.addStaticResource("/engine.properties");
        resourceManager.addStaticResource("/game.xml");
        resourceManager.addStaticResource("/game.properties");
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

    @Override
    protected ICollisionHandler initCollisionHandler() {
        return tmpCollisionHandler;
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
        this.tmpRunLoop = runLoop;
    }

    public void setGOH(IGameObjectHandler goh) {
        if(testPhase()) return;
        this.tmpGOH = goh;
    }

    public void setRenderer(IRenderer renderer) {
        if(testPhase()) return;
        this.tmpRenderer = renderer;
    }

    public void setCollisionHandler(ICollisionHandler collisionHandler) {
        if(testPhase()) return;
        this.tmpCollisionHandler = collisionHandler;
    }
}
