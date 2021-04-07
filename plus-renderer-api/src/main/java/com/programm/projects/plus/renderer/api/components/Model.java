package com.programm.projects.plus.renderer.api.components;

import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.IRenderContext;
import com.programm.projects.plus.core.components.IRenderComponent;
import lombok.Getter;

@Getter
public class Model implements IRenderComponent {

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
     * Component will be set by the renderer after creating a specific Model from the engine in the onInit method
     */
    private IModelComponent modelComponent;

    // SETTINGS FOR THIS MODEL

    private final float[] vertices;
    private final int[] indices;

    public Model(float[] vertices, int[] indices) {
        this.vertices = vertices;
        this.indices = indices;
    }

    @Override
    public void init(IEngineContext context) {
        IRenderContext rendererContext = context.rendererContext();
        rendererContext.init(this);
    }

    public void setModelComponent(IModelComponent modelComponent) {
        this.modelComponent = modelComponent;
    }
}
