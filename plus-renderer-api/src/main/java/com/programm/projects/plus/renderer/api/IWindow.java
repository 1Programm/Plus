package com.programm.projects.plus.renderer.api;

import com.programm.projects.core.lifecycle.ILifecycle;

import java.util.function.Consumer;

public interface IWindow extends ILifecycle {

    String getTitle();
    int getWidth();
    int getHeight();

    void setVisible(boolean visible);

}
