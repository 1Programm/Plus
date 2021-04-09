package com.programm.projects.plus.renderer.api.components;

import lombok.Getter;

@Getter
public class ShapeModel extends Model {

    public enum ShapeType{
        Rectangle,
        Circle,
        Triangle,
    }

    private final ShapeType shapeType;

    //Rectangle
    private final float width, height;

    //Circle
    private final float radius;

    //Triangle
    private final float t1x, t2x, t3x;
    private final float t1y, t2y, t3y;

    public ShapeModel(ShapeType shapeType, float width, float height, float radius, float t1x, float t2x, float t3x, float t1y, float t2y, float t3y) {
        super(Type.Shape);
        this.shapeType = shapeType;
        this.width = width;
        this.height = height;
        this.radius = radius;
        this.t1x = t1x;
        this.t2x = t2x;
        this.t3x = t3x;
        this.t1y = t1y;
        this.t2y = t2y;
        this.t3y = t3y;
    }
}
