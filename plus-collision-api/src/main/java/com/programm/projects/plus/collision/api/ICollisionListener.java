package com.programm.projects.plus.collision.api;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.maths.Vector2f;

public interface ICollisionListener {

    //TODO: Mby convert all args into a collision info class
    void onCollision(GameObject other, Vector2f intersectionNormal);

}
