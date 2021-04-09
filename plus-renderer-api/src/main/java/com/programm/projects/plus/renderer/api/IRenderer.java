package com.programm.projects.plus.renderer.api;

import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.core.IRenderContext;
import com.programm.projects.plus.core.ISubsystem;
import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.core.events.IEventHandler;

public interface IRenderer extends ISubsystem, IRenderContext {

    void setup(IEventHandler eventHandler, WindowInfo windowInfo);

    void addCamera(Camera camera);

    void setRenderableBatch(IObjectBatch renderableBatch);

}
