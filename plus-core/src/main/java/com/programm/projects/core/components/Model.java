package com.programm.projects.core.components;

import com.programm.projects.core.IComponent;
import lombok.Getter;

@Getter
public class Model implements IComponent {

    public static Model Rectangle(float width, float height){
        float w2 = width / 2f;
        float h2 = height / 2f;

        return new Model(
                new float[] {
                        -w2, -h2,
                        -w2, h2,
                        w2, h2,
                        w2, -h2
                },
                new int[] {
                        0, 1, 3,
                        1, 2, 3
                }
        );
    }


    /**
     * Component will be set by the renderer after creating a specific Model from the engine
     */
    private IModelComponent modelComponent;

    // SETTINGS FOR THIS MODEL

    private final float[] vertices;
    private final int[] indices;

    public Model(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    public void setModelComponent(IModelComponent modelComponent) {
        this.modelComponent = modelComponent;
    }
}
