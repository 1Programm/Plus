package com.programm.projects.plus.engine.api.events;

import com.programm.projects.plus.engine.api.IEngine;

public class EngineShutdownEvent extends EngineEvent{

    public EngineShutdownEvent(IEngine engine) {
        super(engine);
    }
}
