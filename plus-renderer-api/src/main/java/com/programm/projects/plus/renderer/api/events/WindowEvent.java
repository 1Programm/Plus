package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.core.events.IEvent;
import com.programm.projects.plus.renderer.api.IWindow;

class WindowEvent implements IEvent {

    private final IWindow window;

    public WindowEvent(IWindow window) {
        this.window = window;
    }

    public IWindow getWindow() {
        return window;
    }
}
