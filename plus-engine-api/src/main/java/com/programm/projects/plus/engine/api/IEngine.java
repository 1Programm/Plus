package com.programm.projects.plus.engine.api;

import com.programm.projects.core.lifecycle.IChainableLifecycle;

public interface IEngine extends IChainableLifecycle {

    void setRunLoop(IRunLoop runLoop);

}
