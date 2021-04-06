package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.renderer.api.IMouse;

class SwingMouse implements IMouse {

    int x, y;
    int dragX, dragY;
    int scroll;
    final boolean[] buttonPressed = new boolean[MAX];

    @Override
    public int getX() {
        return x;
    }

    @Override
    public int getY() {
        return y;
    }

    @Override
    public int getScroll() {
        return scroll;
    }

    @Override
    public int getDragStartX() {
        return dragX;
    }

    @Override
    public int getDragStartY() {
        return dragY;
    }

    @Override
    public boolean isButtonPressed(int button) {
        if(button < 0 || button >= MAX) return false;
        return buttonPressed[button];
    }
}
