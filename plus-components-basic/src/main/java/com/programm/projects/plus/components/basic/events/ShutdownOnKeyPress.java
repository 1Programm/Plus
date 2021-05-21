package com.programm.projects.plus.components.basic.events;

import com.programm.projects.plus.core.events.EventListener;
import com.programm.projects.plus.engine.api.IEngine;
import com.programm.projects.plus.renderer.api.IKeyboard;
import com.programm.projects.plus.renderer.api.events.KeyPressedEvent;

public class ShutdownOnKeyPress implements EventListener<KeyPressedEvent> {

    public static ShutdownOnKeyPress Escape(IEngine engine){
        return new ShutdownOnKeyPress(engine, IKeyboard.KEY_ESCAPE);
    }

    private final IEngine engine;
    private final int keyCode;

    public ShutdownOnKeyPress(IEngine engine, int keyCode) {
        this.engine = engine;
        this.keyCode = keyCode;

        engine.events().listenFor(KeyPressedEvent.class, this);
    }

    @Override
    public void onEvent(KeyPressedEvent event) {
        if(event.getKeyCode() == keyCode && !event.isRepeated()){
            engine.handleException(engine::shutdown);
        }
    }
}
