package com.programm.projects.plus.renderer.api.events;

import com.programm.projects.core.events.ConsumableEvent;
import com.programm.projects.plus.renderer.api.IKeyboard;
import lombok.Getter;

@Getter
public class KeyEvent extends ConsumableEvent implements IKeyboard {

    private final IKeyboard keyboard;
    private final int keyCode;

    public KeyEvent(IKeyboard keyboard, int keyCode) {
        this.keyboard = keyboard;
        this.keyCode = keyCode;
    }

    @Override
    public boolean isKeyPressed(int keyCode) {
        return keyboard.isKeyPressed(keyCode);
    }
}
