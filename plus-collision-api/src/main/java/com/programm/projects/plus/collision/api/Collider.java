package com.programm.projects.plus.collision.api;

import com.programm.projects.plus.core.AbstractComponent;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.collision.ColliderType;
import com.programm.projects.plus.maths.Vector2f;
import com.programm.projects.plus.renderer.api.components.ShapeModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Collider extends AbstractComponent {

    public static Collider AABB(){
        return new Collider(ColliderType.AABB, false, null);
    }

    public static Collider AABB(float minX, float minY, float maxX, float maxY){
        return new Collider(ColliderType.AABB, false, new AABB(minX, minY, maxX, maxY));
    }

    private ColliderType type;
    private boolean dynamic;

    public AABB aabb;

    public Collider() {}

    @Override
    public void init(IEngineContext context) {
        ShapeModel model = parent.getComponent(ShapeModel.class);

        if(model == null) return;

        if(type == null){
            type = context.collision().preferredCollider();
        }

        if(type == ColliderType.AABB){
            if(aabb == null) {
                aabb = new AABB();
                initAABB(model);
            }
        }
    }

    private void initAABB(ShapeModel model){
        if(model.getShapeType() == ShapeModel.ShapeType.Rectangle) {
            float w = model.getSize().getX() / 2f;
            float h = model.getSize().getY() / 2f;
            aabb.setMaxX(w);
            aabb.setMaxY(h);
            aabb.setMinX(-w);
            aabb.setMinY(-h);
        }
        else if(model.getShapeType() == ShapeModel.ShapeType.Line){
            Vector2f pos1 = model.getL1();
            Vector2f pos2 = model.getL2();

            if(pos1.getX() <= pos2.getX()){
                aabb.setMinX(pos1.getX());
                aabb.setMaxX(pos2.getX());
            }
            else {
                aabb.setMinX(pos2.getX());
                aabb.setMaxX(pos1.getX());
            }

            if(pos1.getY() <= pos2.getY()){
                aabb.setMinY(pos1.getY());
                aabb.setMaxY(pos2.getY());
            }
            else {
                aabb.setMinY(pos2.getY());
                aabb.setMaxY(pos1.getY());
            }
        }
        else {//CIRCLE
            float radius = model.getRadius().getVal();
            aabb.setMaxX(radius);
            aabb.setMaxY(radius);
            aabb.setMinX(-radius);
            aabb.setMinY(-radius);
        }
    }
}
