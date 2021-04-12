package com.programm.projects.plus.engine.api;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IGameContext;

public class GameContext implements IGameContext {

    @Override
    public GameObject getObject() {
        return null;
    }

    @Override
    public double getDelta() {
        return 0;
    }
}
