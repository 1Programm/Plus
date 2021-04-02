package com.programm.plus.collision.api;

import com.programm.plus.core.obj.GameObject;

public interface CollisionChecker {

    boolean collides(GameObject o1, GameObject o2);

}
