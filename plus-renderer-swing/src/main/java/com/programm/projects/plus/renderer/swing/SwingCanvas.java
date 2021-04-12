package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.core.components.Transform;
import com.programm.projects.plus.renderer.api.components.ColorMaterial;
import com.programm.projects.plus.renderer.api.components.IRenderModel;
import com.programm.projects.plus.renderer.api.components.Model;
import com.programm.projects.plus.renderer.swing.components.ISwingRenderModel;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.List;

class SwingCanvas extends Canvas {

    public void render(IObjectBatch renderableBatch, Color backgroundColor, Camera camera){
        BufferStrategy bs = getBufferStrategy();

        if(bs == null){
            createBufferStrategy(2);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        g.setColor(backgroundColor);
        g.fillRect(0, 0, getWidth(), getHeight());

        Graphics2D g2d = (Graphics2D)g;

        float canvasWidth = getWidth();
        float canvasHeight = getHeight();
        float camX = camera.getX() - canvasWidth / 2;
        float camY = camera.getY() - canvasHeight / 2;
        g2d.translate(-camX, -camY);

        g2d.setColor(Color.BLACK);
        for(GameObject obj : renderableBatch){

            ColorMaterial colorMaterial = obj.getComponent(ColorMaterial.class);
            List<Model> models = obj.getComponentList(Model.class);

            if(models.size() != 0 && colorMaterial != null){
                Transform transform = obj.getTransform();
                int x = (int)transform.getX();
                int y = (int)transform.getY();
                float scaleX = transform.getXScale();
                float scaleY = transform.getYScale();
                float rotation = transform.getRotation();

                g2d.translate(x, y);
                g2d.scale(scaleX, scaleY);
                g2d.rotate(rotation);


                Model model = models.get(0);
                IRenderModel renderModel = model.getRenderModel();

                if(renderModel instanceof ISwingRenderModel swingModel) {
                    Color fillColor = colorMaterial.getFillColor();

                    if(fillColor != null){
                        g2d.setColor(fillColor);
                        swingModel.renderFill(g2d);
                    }

                    Color borderColor = colorMaterial.getBorderColor();

                    if(borderColor != null){
                        g2d.setColor(borderColor);
                        swingModel.renderBounds(g2d);
                    }
                }

                g2d.rotate(-rotation);
                g2d.scale(1/scaleX, 1/scaleY);
                g2d.translate(-x, -y);
            }
        }


        g2d.translate(camX, camY);

        g.dispose();
        bs.show();
    }

}
