package com.programm.projects.plus.core.events;

import com.programm.projects.plus.core.components.Camera;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class RegisterCameraEvent implements IEvent{

    private final Camera camera;

    @Override
    public boolean immediate() {
        return true;
    }
}
