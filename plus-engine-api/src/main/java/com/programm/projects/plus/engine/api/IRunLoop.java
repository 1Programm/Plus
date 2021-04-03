package com.programm.projects.plus.engine.api;

import com.programm.projects.core.lifecycle.ILifecycle;

public interface IRunLoop extends ILifecycle {

    void init(Runnable updateCallback);

}
