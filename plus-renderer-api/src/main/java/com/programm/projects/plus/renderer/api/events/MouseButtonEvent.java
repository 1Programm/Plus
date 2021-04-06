package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.renderer.api.IMouse;
import lombok.Getter;

public class MouseButtonEvent extends MouseEvent{

    @Getter
    private final int button;

    public MouseButtonEvent(IMouse mouseInfo, int button) {
        super(mouseInfo);
        this.button = button;
    }
}
