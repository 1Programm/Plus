package com.programm.projects.plus.renderer.api;

import com.programm.projects.core.IUpdatable;
import com.programm.projects.core.lifecycle.ILifecycle;

public interface IRenderer extends ILifecycle, IUpdatable {

    void createWindow(int width, int height, String title);

    IWindow getWindow();


    void setRenderableObjectBatch(IRenderableObjectBatch objectBatch);

}
