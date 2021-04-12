package com.programm.projects.plus.renderer.api.components;

import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.components.IRenderComponent;
import com.programm.projects.plus.maths.Vector1f;
import com.programm.projects.plus.maths.Vector2f;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
public class Model implements IRenderComponent {

    public static Model Rectangle(Vector2f size){
        ShapeModel model = new ShapeModel(ShapeModel.ShapeType.Rectangle);
        model.setSize(size);

        return model;
    }

    public static Model Rectangle(float width, float height){
        return Rectangle(new Vector2f(width, height));
    }

    public static Model Circle(Vector1f radius){
        ShapeModel model = new ShapeModel(ShapeModel.ShapeType.Circle);
        model.setRadius(radius);

        return model;
    }

    public static Model Circle(float radius){
        return Circle(new Vector1f(radius));
    }

    public static Model Line(Vector2f l1, Vector2f l2){
        ShapeModel model = new ShapeModel(ShapeModel.ShapeType.Line);
        model.setL1(l1);
        model.setL2(l2);

        return model;
    }

    public static Model Line(float l1x, float l1y, float l2x, float l2y){
        return Line(new Vector2f(l1x, l1y), new Vector2f(l2x, l2y));
    }

    public static Model Mesh(int dimensions, float[] vertices, int[] indices){
        return new MeshModel(dimensions, vertices, indices);
    }

    public enum Type{
        Mesh,
        Shape
    }

    private final Type type;
    private IRenderModel renderModel;

    @Override
    public void init(IEngineContext context) {
        context.rendererContext().init(this);
    }
}
