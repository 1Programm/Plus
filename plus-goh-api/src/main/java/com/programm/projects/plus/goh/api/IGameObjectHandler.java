package com.programm.projects.plus.goh.api;

import com.programm.projects.plus.core.*;
import com.programm.projects.plus.maths.Vector2f;

public interface IGameObjectHandler extends ISubsystem, IObjectConsumer {

    void setup(IEngineContext engineContext, IRunLoopInfo runLoopInfo);

    void update();

    /**
     * Method to retrieve relevant Objects for rendering
     * @return an IObjectBatch which contains objects sorted and ready for rendering
     */
    IObjectBatch getObjectBatch();

    IObjectBatch getNearestObjectsTo(GameObject object, Vector2f velocity);

}
