package com.programm.projects.plus.renderer.api;

import com.programm.projects.core.IUpdatable;
import com.programm.projects.core.lifecycle.ILifecycle;

public interface IRenderer extends ILifecycle, IUpdatable {

    void createWindow(String title, int width, int height);

    IWindow getWindow();


    void setRenderableObjectBatch(IRenderableObjectBatch objectBatch);

}
