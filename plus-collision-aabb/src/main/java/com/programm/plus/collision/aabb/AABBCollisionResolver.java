package com.programm.plus.collision.aabb;

import com.programm.plus.collision.api.CollisionResolver;
import com.programm.plus.core.component.Mover;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.maths.Vector2f;

public class AABBCollisionResolver implements CollisionResolver<AABBCollisionInfo> {

    @Override
    public void resolve(AABBCollisionInfo info, GameObject obj) {
        Vector2f offset = info.getOffset().clone().mul(-1);

        if(Math.abs(offset.getX()) < Math.abs(offset.getY())){
            offset.setY(0);
        }
        else {
            offset.setX(0);
        }

        Mover mover = obj.getComponents().getMover();
        if(mover != null) {
            mover.move(offset);
        }
    }
}
