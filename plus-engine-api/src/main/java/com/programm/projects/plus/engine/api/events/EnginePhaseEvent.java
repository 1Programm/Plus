package com.programm.projects.plus.engine.api.events;

import com.programm.projects.plus.engine.api.EnginePhase;
import com.programm.projects.plus.engine.api.IEngine;
import lombok.Getter;

@Getter
public class EnginePhaseEvent extends EngineEvent{

    private final EnginePhase before;
    private final EnginePhase phase;

    public EnginePhaseEvent(IEngine engine, EnginePhase before) {
        super(engine);
        this.before = before;
        this.phase = engine.phase();
    }

    @Override
    public boolean immediate() {
        return true;
    }
}
