package com.programm.plus.render.jcanvas;

import lombok.Getter;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WindowListener extends WindowAdapter {

    @Getter private boolean shouldClose;

    @Override
    public void windowClosing(WindowEvent e) {
        shouldClose = true;
    }
}
