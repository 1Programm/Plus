package com.programm.projects.plus.goh.api;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IUpdatable;
import com.programm.projects.core.lifecycle.ILifecycle;

public interface IGameObjectHandler extends ILifecycle, IUpdatable {

    void addObject(GameObject object);

}
