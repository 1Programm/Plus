package com.programm.projects.plus.collision.api;

import com.programm.projects.plus.core.ICollisionContext;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.ISubsystem;

public interface ICollisionHandler extends ISubsystem, ICollisionContext {

    void setup(IEngineContext engineContext);

}
