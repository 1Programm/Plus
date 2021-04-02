package com.programm.plus.collision.aabb;

import com.programm.plus.collision.api.CollisionChecker;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.maths.Vector2f;

public class AABBCollisionChecker implements CollisionChecker {

    @Override
    public boolean collides(GameObject o1, GameObject o2) {
        Vector2f pos1 = o1.getTransform().getPosition();
        Vector2f size1 = o1.getTransform().getSize();
        Vector2f pos2 = o2.getTransform().getPosition();
        Vector2f size2 = o2.getTransform().getSize();

        if(pos1.getX() > pos2.getX() + size2.getX()) return false;
        if(pos1.getX() + size1.getX() < pos2.getX()) return false;
        if(pos1.getY() > pos2.getY() + size2.getY()) return false;
        if(pos1.getY() + size1.getY() < pos2.getY()) return false;

        return true;
    }
}
