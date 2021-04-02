package com.programm.plus.collision.api;

import com.programm.plus.core.obj.GameObject;

public interface CollisionInfoCollector <T extends CollisionInfo> {

    void collect(T info, GameObject o1, GameObject o2);

}
