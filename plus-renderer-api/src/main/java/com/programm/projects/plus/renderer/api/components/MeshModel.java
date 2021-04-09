package com.programm.projects.plus.renderer.api.components;

import lombok.Getter;

@Getter
public class MeshModel extends Model {

    private final int dimensions;
    private final float[] vertices;
    private final int[] indices;

    public MeshModel(int dimensions, float[] vertices, int[] indices) {
        super(Type.Mesh);
        this.dimensions = dimensions;
        this.vertices = vertices;
        this.indices = indices;
    }

}
