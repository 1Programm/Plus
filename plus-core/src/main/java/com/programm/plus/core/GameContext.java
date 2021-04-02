package com.programm.plus.core;

import com.programm.plus.engine.api.settings.EngineSettings;
import com.programm.plus.goh.api.GameObjectHandler;
import com.programm.plus.render.api.GameWindow;
import com.programm.plus.render.api.Pencil;
import com.programm.plus.render.api.controlls.Controlls;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Supplier;

@AllArgsConstructor
public class GameContext {

    @Getter private GameWindow window;
    private Supplier<Float> deltaTimeSupplier;
    @Getter private Pencil pencil;
    @Getter private GameObjectHandler objectHandler;
    @Getter private EngineSettings engineSettings;

    public float getDeltaTime(){
        return deltaTimeSupplier.get();
    }

    public Controlls getControlls(){
        return window.getControlls();
    }

}
