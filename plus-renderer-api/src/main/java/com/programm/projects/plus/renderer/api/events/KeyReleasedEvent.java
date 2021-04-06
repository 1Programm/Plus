package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.renderer.api.IKeyboard;

public class KeyReleasedEvent extends KeyEvent{

    public KeyReleasedEvent(IKeyboard keyboard, int keyCode) {
        super(keyboard, keyCode);
    }
}
