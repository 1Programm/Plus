package com.programm.projects.plus.renderer.api;

import com.programm.projects.core.IObjectBatch;
import com.programm.projects.core.ISubsystem;
import com.programm.projects.core.events.IEventHandler;

public interface IRenderer extends ISubsystem {

    void setup(IEventHandler eventHandler, WindowInfo windowInfo);

    void setRenderableBatch(IObjectBatch renderableBatch);

}
