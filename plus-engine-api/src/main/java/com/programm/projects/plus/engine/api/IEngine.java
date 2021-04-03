package com.programm.projects.plus.engine.api;

import com.programm.projects.core.IGameContext;
import com.programm.projects.core.lifecycle.IChainableLifecycle;
import com.programm.projects.plus.goh.api.IGameObjectHandler;

public interface IEngine extends IChainableLifecycle {

    void setRunLoop(IRunLoop runLoop);
    void setGOH(IGameObjectHandler goh);


    //Temporary - replace by Initializer - System
    IGameObjectHandler getGOH();

}
