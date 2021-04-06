package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.renderer.api.IMouse;

public class MousePressedEvent extends MouseButtonEvent{
    public MousePressedEvent(IMouse mouseInfo, int button) {
        super(mouseInfo, button);
    }
}
