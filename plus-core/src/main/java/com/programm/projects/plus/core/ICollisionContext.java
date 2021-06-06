package com.programm.projects.plus.core;

import com.programm.projects.plus.core.collision.ColliderType;
import com.programm.projects.plus.maths.Vector1f;
import com.programm.projects.plus.maths.Vector2f;

public interface ICollisionContext {

    ColliderType preferredCollider();

    void checkMovement(GameObject object, Vector2f velocity);
    void checkRotation(GameObject object, Vector1f rotationVelocity);

}
