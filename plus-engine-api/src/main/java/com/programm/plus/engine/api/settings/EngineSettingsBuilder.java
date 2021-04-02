package com.programm.plus.engine.api.settings;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Setter
public class EngineSettingsBuilder{

    private boolean rotationEnabled;

    public EngineSettings build(){
        log.debug("Rotation {}.", rotationEnabled ? "enabled" : "disabled");

        return new EngineSettings(rotationEnabled);
    }
}