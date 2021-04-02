package com.programm.plus.collision.api;

import com.programm.plus.core.obj.GameObject;

public interface CollisionTester {

    void test(GameObject o1, GameObject o2, Collider collider);

}
