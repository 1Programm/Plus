package com.programm.projects.plus.engine.api.events;

import com.programm.projects.plus.engine.api.IEngine;

public class EngineStartupEvent extends EngineEvent{

    public EngineStartupEvent(IEngine engine) {
        super(engine);
    }

}
