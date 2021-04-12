package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.core.IRenderContext;
import com.programm.projects.plus.core.components.IRenderComponent;
import com.programm.projects.plus.renderer.api.components.Model;
import com.programm.projects.plus.renderer.api.components.IRenderModel;
import com.programm.projects.plus.renderer.api.components.MeshModel;
import com.programm.projects.plus.renderer.api.components.ShapeModel;
import com.programm.projects.plus.renderer.swing.components.*;

public class SwingComponentPreparer implements IRenderContext {

    @Override
    public void init(IRenderComponent component) {
        if(component instanceof Model){
            prepareModel((Model)component);
        }
    }

    private void prepareModel(Model model){
        IRenderModel renderModel = model.getRenderModel();

        if(!(renderModel instanceof ISwingRenderModel)) {
            if(model.getType() == Model.Type.Mesh) {
                MeshModel mModel = (MeshModel) model;

                int dim = mModel.getDimensions();

                if(dim != 2){
                    throw new IllegalArgumentException("Swing Renderer does only support 2D models! (Dimensions specified: " + dim + ")");
                }

                float[] vertices = mModel.getVertices();

                int length = vertices.length / 2;

                int[] xPoints = new int[length];
                int[] yPoints = new int[length];

                for (int i = 0; i < length; i++) {
                    xPoints[i] = (int) vertices[i * 2];
                    yPoints[i] = (int) vertices[i * 2 + 1];
                }

                renderModel = new SwingPolygonModel(xPoints, yPoints, length);
            }
            else {
                ShapeModel sModel = (ShapeModel) model;
                ShapeModel.ShapeType type = sModel.getShapeType();

                if(type == ShapeModel.ShapeType.Rectangle){
                    renderModel = new SwingRectangleModel(sModel);
                }
                else if(type == ShapeModel.ShapeType.Circle){
                    renderModel = new SwingCircleModel(sModel);
                }
                else { //if(type == ShapeModel.ShapeType.Line)
                    renderModel = new SwingLineModel(sModel);
                }
            }

            model.setRenderModel(renderModel);
        }
    }
}
