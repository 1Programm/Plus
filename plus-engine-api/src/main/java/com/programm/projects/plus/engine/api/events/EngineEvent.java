package com.programm.projects.plus.engine.api.events;

import com.programm.projects.plus.core.events.IEvent;
import com.programm.projects.plus.engine.api.IEngine;
import lombok.Getter;

@Getter
public class EngineEvent implements IEvent {

    private final IEngine engine;

    public EngineEvent(IEngine engine) {
        this.engine = engine;
    }

}
