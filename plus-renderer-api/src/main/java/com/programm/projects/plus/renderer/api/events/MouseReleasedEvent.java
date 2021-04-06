package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.renderer.api.IMouse;

public class MouseReleasedEvent extends MouseButtonEvent{
    public MouseReleasedEvent(IMouse mouseInfo, int button) {
        super(mouseInfo, button);
    }
}
