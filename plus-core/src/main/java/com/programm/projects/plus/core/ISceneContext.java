package com.programm.projects.plus.core;

import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.maths.Vector2f;

public interface ISceneContext {

    void addObject(GameObject object);

    IObjectBatch getNearestObjectsTo(GameObject object, Vector2f velocity);

//    Get camera by name
//    Camera getCamera(String name);

    Camera getCurrentCamera();

}
