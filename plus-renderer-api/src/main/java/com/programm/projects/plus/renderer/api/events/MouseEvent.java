package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.core.events.ConsumableEvent;
import com.programm.projects.plus.renderer.api.IMouse;

public class MouseEvent extends ConsumableEvent implements IMouse{

    private final IMouse mouseInfo;

    public MouseEvent(IMouse mouseInfo) {
        this.mouseInfo = mouseInfo;
    }

    @Override
    public int getX() {
        return mouseInfo.getX();
    }

    @Override
    public int getY() {
        return mouseInfo.getY();
    }

    @Override
    public int getScroll() {
        return mouseInfo.getScroll();
    }

    @Override
    public int getDragStartX() {
        return mouseInfo.getDragStartX();
    }

    @Override
    public int getDragStartY() {
        return mouseInfo.getDragStartY();
    }

    @Override
    public boolean isButtonPressed(int button) {
        return mouseInfo.isButtonPressed(button);
    }
}
