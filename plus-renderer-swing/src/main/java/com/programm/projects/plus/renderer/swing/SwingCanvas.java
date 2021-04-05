package com.programm.projects.plus.renderer.swing;

import com.programm.projects.core.GameObject;
import com.programm.projects.core.IObjectBatch;
import com.programm.projects.core.components.ColorMaterial;
import com.programm.projects.core.components.Transform;
import com.programm.projects.plus.renderer.swing.components.SwingShape;

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

            SwingShape shape = obj.getComponent(SwingShape.class);

            if(shape != null){
                g2d.draw(shape.getShape());
            }

            g2d.scale(-scaleX, -scaleY);
            g2d.translate(-x, -y);
        }

        g.dispose();
        bs.show();
    }

}
