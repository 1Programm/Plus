package com.programm.projects.plus.core;

public interface IGameContext {

    IEngineContext engine();

    double getDelta();

    // INFO ABOUT THE PARENT GAMEOBJECT AND ITS STATE
    GameObject getObject();

    // TEMPORARY (better name)
    boolean enteredUpdateRange();

}
