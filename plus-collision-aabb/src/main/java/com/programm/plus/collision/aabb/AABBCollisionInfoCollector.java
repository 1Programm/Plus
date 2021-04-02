package com.programm.plus.collision.aabb;

import com.programm.plus.collision.api.CollisionInfoCollector;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.maths.Vector2f;

public class AABBCollisionInfoCollector implements CollisionInfoCollector<AABBCollisionInfo> {

    @Override
    public void collect(AABBCollisionInfo info, GameObject o1, GameObject o2) {
        float o1Left = o1.getTransform().getPosition().getX();
        float o2Left = o2.getTransform().getPosition().getX();
        float o1Right = o1.getTransform().getPosition().getX() + o1.getTransform().getSize().getX();
        float o2Right = o2.getTransform().getPosition().getX() + o2.getTransform().getSize().getX();

        float o1Top = o1.getTransform().getPosition().getY();
        float o2Top = o2.getTransform().getPosition().getY();
        float o1Btm = o1.getTransform().getPosition().getY() + o1.getTransform().getSize().getY();
        float o2Btm = o2.getTransform().getPosition().getY() + o2.getTransform().getSize().getY();

        Vector2f offset = new Vector2f();

        if(o1Right < o2Right){
            float xIn = o1Right - o2Left;
            if(xIn > 0){
                offset.setX(xIn);
            }
        }
        else {
            float xIn = o1Left - o2Right;
            if(xIn < 0){
                offset.setX(xIn);
            }
        }

        if(o1Btm < o2Btm){
            float yIn = o1Btm - o2Top;
            if(yIn > 0){
                offset.setY(yIn);
            }
        }
        else {
            float yIn = o1Top - o2Btm;
            if(yIn < 0){
                offset.setY(yIn);
            }
        }

        info.setOffset(offset);
    }
}
