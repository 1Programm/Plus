package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.renderer.api.IKeyboard;
import com.programm.projects.plus.renderer.api.events.KeyPressedEvent;

class SwingKeyboard implements IKeyboard {

    final int[] keys = new int[KeyPressedEvent.MAX];

    @Override
    public boolean isKeyPressed(int keyCode) {
        if(keyCode < 0 || keyCode >= MAX) return false;
        return keys[keyCode] > 0;
    }
}
