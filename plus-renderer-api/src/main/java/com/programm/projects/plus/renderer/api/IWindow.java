package com.programm.projects.plus.renderer.api;

import com.programm.projects.plus.core.lifecycle.ILifecycle;

public interface IWindow extends ILifecycle {

    String getTitle();
    int getWidth();
    int getHeight();

    void setVisible(boolean visible);

}
