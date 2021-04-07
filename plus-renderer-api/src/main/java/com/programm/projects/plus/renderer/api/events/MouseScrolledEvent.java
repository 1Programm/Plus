package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.renderer.api.IMouse;
import lombok.Getter;

@Getter
public class MouseScrolledEvent extends MouseEvent{

    //Scroll change
    private final int change;

    public MouseScrolledEvent(IMouse mouseInfo, int change) {
        super(mouseInfo);
        this.change = change;
    }

}
