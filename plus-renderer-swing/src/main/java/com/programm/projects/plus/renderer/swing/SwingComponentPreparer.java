package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.core.IRenderContext;
import com.programm.projects.plus.core.components.IRenderComponent;
import com.programm.projects.plus.renderer.api.components.IModelComponent;
import com.programm.projects.plus.renderer.api.components.Model;
import com.programm.projects.plus.renderer.swing.components.SwingModelComponent;

import java.awt.*;

public class SwingComponentPreparer implements IRenderContext {

    @Override
    public void init(IRenderComponent component) {
        if(component instanceof Model){
            prepareModel((Model)component);
        }
    }

    private void prepareModel(Model model){
        IModelComponent modelComponent = model.getModelComponent();
        Shape shape;

        if(!(modelComponent instanceof SwingModelComponent)) {
            float[] vertices = model.getVertices();
            int length = vertices.length / 2;

            int[] xPoints = new int[length];
            int[] yPoints = new int[length];

            for (int i = 0; i < length; i++) {
                xPoints[i] = (int) vertices[i * 2];
                yPoints[i] = (int) vertices[i * 2 + 1];
            }

            shape = new Polygon(xPoints, yPoints, length);
            modelComponent = new SwingModelComponent(shape);
            model.setModelComponent(modelComponent);
        }
    }
}
