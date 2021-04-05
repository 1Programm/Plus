package com.programm.projects.plus.goh.api;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IObjectBatch;
import com.programm.projects.core.IUpdatable;
import com.programm.projects.core.lifecycle.ILifecycle;

public interface IGameObjectHandler extends ILifecycle, IUpdatable {

    void addObject(GameObject object);

    /**
     * Method to retrieve relevant Objects for rendering
     * @return an IObjectBatch which contains objects sorted and ready for rendering
     */
    IObjectBatch getObjectBatch();

}
