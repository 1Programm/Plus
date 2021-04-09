package com.programm.projects.plus.renderer.api.components;

import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.components.IRenderComponent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
@Getter
@Setter
public class Model implements IRenderComponent {

    public static Model Rectangle(float width, float height){
        return new ShapeModel(ShapeModel.ShapeType.Rectangle, width, height, 0, 0, 0, 0, 0, 0, 0);
    }

    public static Model Circle(float radius){
        return new ShapeModel(ShapeModel.ShapeType.Circle, 0, 0, radius, 0, 0, 0, 0, 0, 0);
    }

    public static Model Triangle(float t1x, float t1y, float t2x, float t2y, float t3x, float t3y){
        return new ShapeModel(ShapeModel.ShapeType.Triangle, 0, 0, 0, t1x, t2x, t3x, t1y, t2y, t3y);
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
