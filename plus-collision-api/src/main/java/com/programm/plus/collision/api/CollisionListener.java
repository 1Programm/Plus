package com.programm.plus.collision.api;

import com.programm.plus.core.obj.GameObject;

public interface CollisionListener {

    void onCollision(GameObject me, GameObject other);

}
