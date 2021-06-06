package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.IRunLoopInfo;
import com.programm.projects.plus.core.ISubsystem;
import com.programm.projects.plus.core.lifecycle.ILifecycle;

/**
 * Represents a run loop of the engine.
 * The loop should start after engine has entered the {@link EnginePhase#STARTED}!
 */
public interface IRunLoop extends ISubsystem {

    void setup(Runnable updateCallback, IEngineContext context);

    void setSync(int fps);

    IRunLoopInfo info();

}
