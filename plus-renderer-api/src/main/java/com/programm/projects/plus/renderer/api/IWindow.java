package com.programm.projects.plus.renderer.api;

import java.util.function.Consumer;

public interface IWindow {

    String getTitle();
    int getWidth();
    int getHeight();

    void setVisible(boolean visible);

    void addResizeListener(Consumer<IWindow> listener);


}
