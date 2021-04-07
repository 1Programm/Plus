package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.core.lifecycle.ILifecycle;

public interface IRunLoop extends ILifecycle {

    void setup(Runnable updateCallback);

    IRunLoopInfo info();

}
