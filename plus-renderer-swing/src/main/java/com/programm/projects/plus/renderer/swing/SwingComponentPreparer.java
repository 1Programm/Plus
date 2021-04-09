package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.core.IRenderContext;
import com.programm.projects.plus.core.components.IRenderComponent;
import com.programm.projects.plus.renderer.api.components.Model;
import com.programm.projects.plus.renderer.api.components.IRenderModel;
import com.programm.projects.plus.renderer.api.components.MeshModel;
import com.programm.projects.plus.renderer.api.components.ShapeModel;
import com.programm.projects.plus.renderer.swing.components.SwingModelComponent;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SwingComponentPreparer implements IRenderContext {

    @Override
    public void init(IRenderComponent component) {
        if(component instanceof Model){
            prepareModel((Model)component);
        }
    }

    private void prepareModel(Model model){
        IRenderModel renderModel = model.getRenderModel();

        if(!(renderModel instanceof SwingModelComponent)) {
            Shape shape;

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

                shape = new Polygon(xPoints, yPoints, length);
            }
            else {
                ShapeModel sModel = (ShapeModel) model;
                ShapeModel.ShapeType type = sModel.getShapeType();

                if(type == ShapeModel.ShapeType.Rectangle){
                    float width = sModel.getWidth();
                    float height = sModel.getHeight();
                    int x = (int)(width / 2);
                    int y = (int)(height / 2);

                    shape = new Rectangle(-x, -y, (int)width, (int)height);
                }
                else if(type == ShapeModel.ShapeType.Circle){
                    float radius = sModel.getRadius();

                    shape = new Ellipse2D.Float(-radius, -radius, radius * 2, radius * 2);
                }
                else { // if(type == ShapeModel.ShapeType.Triangle)
                    int t1x = (int)sModel.getT1x();
                    int t1y = (int)sModel.getT1y();
                    int t2x = (int)sModel.getT2x();
                    int t2y = (int)sModel.getT2y();
                    int t3x = (int)sModel.getT3x();
                    int t3y = (int)sModel.getT3y();

                    shape = new Polygon(new int[]{t1x, t2x, t3x}, new int[]{t1y, t2y, t3y}, 3);
                }
            }

            renderModel = new SwingModelComponent(shape);
            model.setRenderModel(renderModel);
        }
    }
}
