package com.programm.projects.plus.renderer.api.components;

import com.programm.projects.plus.maths.Vector1f;
import com.programm.projects.plus.maths.Vector2f;
import com.programm.projects.plus.maths.Vectorf;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter(AccessLevel.PACKAGE)
public class ShapeModel extends Model {

    public enum ShapeType{
        Rectangle,
        Circle,
        Line
    }

    private final ShapeType shapeType;

    //Rectangle
    private Vector2f size;

    //Circle
    private Vector1f radius;

    //Triangle
    private Vector2f t1, t2, t3;

    //Line
    private Vector2f l1, l2;

    public ShapeModel(ShapeType shapeType) {
        super(Type.Shape);
        this.shapeType = shapeType;
    }

}
