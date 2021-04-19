package com.programm.projects.plus.core;

import com.programm.projects.plus.core.components.Camera;

public interface ISceneContext {

    void addObject(GameObject object);

//    Get camera by name
//    Camera getCamera(String name);

    Camera getCurrentCamera();

}
