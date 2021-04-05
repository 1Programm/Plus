package com.programm.projects.plus.renderer.api;

import com.programm.projects.core.IObjectBatch;
import com.programm.projects.core.IUpdatable;
import com.programm.projects.core.events.IEventDispatcher;
import com.programm.projects.core.lifecycle.ILifecycle;

public interface IRenderer extends ILifecycle, IUpdatable {

    void init(IEventDispatcher eventDispatcher);

    void createWindow(String title, int width, int height);


    void setRenderableBatch(IObjectBatch renderableBatch);

}
