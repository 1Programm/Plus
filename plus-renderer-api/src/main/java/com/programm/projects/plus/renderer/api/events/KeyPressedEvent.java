package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.plus.renderer.api.IKeyboard;
import lombok.Getter;

@Getter
public class KeyPressedEvent extends KeyEvent{

    private final int repeatCount;

    public KeyPressedEvent(IKeyboard keyboard, int keyCode, int repeatCount) {
        super(keyboard, keyCode);
        this.repeatCount = repeatCount;
    }

    public boolean isRepeated(){
        return repeatCount > 0;
    }
}
