package com.programm.projects.plus.collision.simple;

import com.programm.projects.plus.collision.api.Collider;
import com.programm.projects.plus.collision.api.ICollisionHandler;
import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.core.collision.ColliderType;
import com.programm.projects.plus.maths.Vector1f;
import com.programm.projects.plus.maths.Vector2f;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SimpleCollisionHandler implements ICollisionHandler {

    private boolean enabled;
    private IEngineContext engineContext;

    @Override
    public void setup(IEngineContext engineContext) {
        this.engineContext = engineContext;
    }

    @Override
    public void startup() {
        log.info("[Startup] - Simple Collision Handler");
    }

    @Override
    public void shutdown() {
        log.info("[Shutdown] - Simple Collision Handler");
    }

    @Override
    public boolean setEnabled(boolean enabled) {
        this.enabled = enabled;

        return true;
    }

    @Override
    public ColliderType preferredCollider() {
        return ColliderType.AABB;
    }

    @Override
    public void checkMovement(GameObject object, Vector2f velocity) {
        if(!enabled) return;

        Collider c1 = object.getComponent(Collider.class);
        if(c1 == null) return;

        float vx = velocity.getX();
        float vy = velocity.getY();

        IObjectBatch nearest = engineContext.scene().getNearestObjectsTo(object, velocity);

        int vxSign = 1;
        int vySign = 1;
        float vxOff = 0;
        float vyOff = 0;

        for(GameObject obj : nearest){
            Collider c2 = obj.getComponent(Collider.class);
            if(c2 != null){
                float minX1 = object.getTransform().getX() + c1.aabb.getMinX() + vx;
                float maxX1 = object.getTransform().getX() + c1.aabb.getMaxX() + vx;
                float minY1 = object.getTransform().getY() + c1.aabb.getMinY() + vy;
                float maxY1 = object.getTransform().getY() + c1.aabb.getMaxY() + vy;

                float minX2 = obj.getTransform().getX() + c2.aabb.getMinX();
                float maxX2 = obj.getTransform().getX() + c2.aabb.getMaxX();
                float minY2 = obj.getTransform().getY() + c2.aabb.getMinY();
                float maxY2 = obj.getTransform().getY() + c2.aabb.getMaxY();

                if(maxX1 < minX2) continue;
                if(minX1 > maxX2) continue;
                if(maxY1 < minY2) continue;
                if(minY1 > maxY2) continue;


                float diffX1 = Math.abs(minX1 - maxX2); // RIGHT
                float diffX2 = Math.abs(maxX1 - minX2); // LEFT

                boolean minIsDiffX1 = diffX1 < diffX2;
                float minDiffHorizontal = minIsDiffX1 ? diffX1 : diffX2;

                float diffY1 = Math.abs(minY1 - maxY2); // BOT
                float diffY2 = Math.abs(maxY1 - minY2); // TOP

                boolean minIsDiffY1 = diffY1 < diffY2;
                float minDiffVertical = minIsDiffY1 ? diffY1 : diffY2;

                boolean isHorizontal = minDiffHorizontal < minDiffVertical;

                if(isHorizontal){
                    if(vxOff < minDiffHorizontal){
                        vxOff = minDiffHorizontal;
                        vxSign = minIsDiffX1 ? 1 : -1;
                    }
                }
                else {
                    if(vyOff < minDiffVertical){
                        vyOff = minDiffVertical;
                        vySign = minIsDiffY1 ? 1 : -1;
                    }
                }
            }
        }

        velocity.add(vxOff * vxSign, vyOff * vySign);
    }

    @Override
    public void checkRotation(GameObject object, Vector1f rotationVelocity) {
        //if(!enabled) return;
        //TODO: rotation check
    }
}
