package com.programm.projects.plus.renderer.swing;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IObjectBatch;
import com.programm.projects.core.components.ColorMaterial;
import com.programm.projects.core.components.IModelComponent;
import com.programm.projects.core.components.Model;
import com.programm.projects.core.components.Transform;
import com.programm.projects.plus.renderer.swing.components.SwingModelComponent;

import java.awt.*;
import java.awt.image.BufferStrategy;

class SwingCanvas extends Canvas {

    public void render(IObjectBatch renderableBatch, Color backgroundColor){
        BufferStrategy bs = getBufferStrategy();

        if(bs == null){
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D)g;

        g2d.setColor(Color.BLACK);
        for(GameObject obj : renderableBatch){
            Transform transform = obj.getTransform();
            int x = (int)transform.getX();
            int y = (int)transform.getY();
            float scaleX = transform.getXScale();
            float scaleY = transform.getYScale();

            g2d.translate(x, y);
            g2d.scale(scaleX, scaleY);


            ColorMaterial colorMaterial = obj.getComponent(ColorMaterial.class);

            if(colorMaterial != null){
                g.setColor(colorMaterial.getColor());
            }

            Model model = obj.getComponent(Model.class);

            if(model != null){
                IModelComponent modelComponent = model.getModelComponent();
                Shape shape;


                if(modelComponent instanceof SwingModelComponent) {
                    shape = ((SwingModelComponent)modelComponent).getShape();
                }
                else {
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

                g2d.draw(shape);
            }

            g2d.scale(1/scaleX, 1/scaleY);
            g2d.translate(-x, -y);
        }

        g.dispose();
        bs.show();
    }

}
