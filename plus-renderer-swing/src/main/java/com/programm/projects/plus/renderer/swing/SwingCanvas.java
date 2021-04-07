package com.programm.projects.plus.renderer.swing;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IObjectBatch;
import com.programm.projects.plus.renderer.api.components.ColorMaterial;
import com.programm.projects.plus.renderer.api.components.IModelComponent;
import com.programm.projects.plus.renderer.api.components.Model;
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
            Model model = obj.getComponent(Model.class);

            if(model != null && colorMaterial != null){
                IModelComponent modelComponent = model.getModelComponent();

                if(modelComponent instanceof SwingModelComponent) {
                    Shape shape = ((SwingModelComponent) modelComponent).getShape();

                    Color fillColor = colorMaterial.getFillColor();

                    if(fillColor != null){
                        g2d.setColor(fillColor);
                        g2d.fill(shape);
                    }

                    Color borderColor = colorMaterial.getBorderColor();

                    if(borderColor != null){
                        g2d.setColor(borderColor);
                        g2d.draw(shape);
                    }
                }
            }

            g2d.scale(1/scaleX, 1/scaleY);
            g2d.translate(-x, -y);
        }

        g.dispose();
        bs.show();
    }

}
