package com.programm.projects.plus.renderer.api;

import com.programm.projects.plus.core.*;
import com.programm.projects.plus.core.components.Camera;

public interface IRenderer extends ISubsystem, IRenderContext {

    void setup(IEngineContext context);

    void addCamera(Camera camera);

    void setRenderableBatch(IObjectBatch renderableBatch);

}
