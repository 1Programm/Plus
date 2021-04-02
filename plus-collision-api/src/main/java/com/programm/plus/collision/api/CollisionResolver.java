package com.programm.plus.collision.api;

import com.programm.plus.core.obj.GameObject;

public interface CollisionResolver<T extends CollisionInfo> {

    void resolve(T info, GameObject obj);

}
