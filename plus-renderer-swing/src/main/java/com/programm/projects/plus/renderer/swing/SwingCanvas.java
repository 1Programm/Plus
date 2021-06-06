package com.programm.projects.plus.renderer.swing;

import com.programm.projects.plus.core.GameObject;
import com.programm.projects.plus.core.IEngineContext;
import com.programm.projects.plus.core.IObjectBatch;
import com.programm.projects.plus.core.components.Camera;
import com.programm.projects.plus.core.components.Transform;
import com.programm.projects.plus.core.resource.Resource;
import com.programm.projects.plus.renderer.api.components.*;
import com.programm.projects.plus.renderer.swing.components.ISwingRenderModel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SwingCanvas extends Canvas {

    private final Map<String, BufferedImage> images = new HashMap<>();

    public void render(IEngineContext context, IObjectBatch renderableBatch, Color backgroundColor, Camera camera){
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

            List<Material> materials = obj.getComponentList(Material.class);
            List<Model> models = obj.getComponentList(Model.class);

            if(!models.isEmpty() && ! materials.isEmpty()){
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
                Material material = materials.get(0);

                IRenderModel renderModel = model.getRenderModel();

                if(material instanceof ColorMaterial colorMaterial) {
                    if (renderModel instanceof ISwingRenderModel swingModel) {
                        Color fillColor = colorMaterial.getFillColor();

                        if (fillColor != null) {
                            g2d.setColor(fillColor);
                            swingModel.renderFill(g2d);
                        }

                        Color borderColor = colorMaterial.getBorderColor();

                        if (borderColor != null) {
                            g2d.setColor(borderColor);
                            swingModel.renderBounds(g2d);
                        }
                    }
                }
                else if(material instanceof ImageMaterial imageMaterial){
                    if (renderModel instanceof ISwingRenderModel swingModel) {
                        String resourcePath = imageMaterial.getResourcePath();
                        BufferedImage image = images.get(resourcePath);

                        if(image == null){
                            //TODO: not dynamic but on scene initialization !!!
                            Resource resource = context.resources().getResource(resourcePath);

                            File f = resource.asFile();
                            try {
                                image = ImageIO.read(f);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            images.put(resourcePath, image);
                        }

                        swingModel.renderImage(g2d, image);
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
