package com.programm.projects.plus.goh.api;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.core.ISubsystem;

public interface IGameObjectHandler extends ISubsystem {

    void setup(IEngineContext engineContext);

    void addObject(GameObject object);

    /**
     * Method to retrieve relevant Objects for rendering
     * @return an IObjectBatch which contains objects sorted and ready for rendering
     */
    IObjectBatch getObjectBatch();

}
