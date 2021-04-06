package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.renderer.api.IMouse;

public class MouseMovedEvent extends MouseEvent{

    public MouseMovedEvent(IMouse mouseInfo) {
        super(mouseInfo);
    }
}
