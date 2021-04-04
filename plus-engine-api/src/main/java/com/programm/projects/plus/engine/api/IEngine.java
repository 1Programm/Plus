package com.programm.projects.plus.engine.api;

import com.programm.projects.core.events.IEventHandler;
import com.programm.projects.core.lifecycle.IChainableLifecycle;
import com.programm.projects.plus.goh.api.IGameObjectHandler;
import com.programm.projects.plus.renderer.api.IRenderer;

public interface IEngine extends IChainableLifecycle {

    void setRunLoop(IRunLoop runLoop);
    void setGOH(IGameObjectHandler goh);
    void setRenderer(IRenderer renderer);


    //Temporary - replace by Initializer - System
    IGameObjectHandler getGOH();

    IEventHandler events();
}
