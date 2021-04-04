package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.renderer.api.IWindow;

public class WindowCloseEvent extends WindowEvent{

    public WindowCloseEvent(IWindow window) {
        super(window);
    }

}
