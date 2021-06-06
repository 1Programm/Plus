package com.programm.projects.plus.renderer.swing.components;

import com.programm.projects.plus.maths.Vector2f;
import com.programm.projects.plus.renderer.api.components.ShapeModel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SwingRectangleModel implements ISwingRenderModel{

    private final Vector2f size;

    public SwingRectangleModel(ShapeModel model) {
        this.size = model.getSize();
    }

    @Override
    public void renderFill(Graphics2D g2d) {
        int w = (int)size.getX();
        int h = (int)size.getY();
        int x = -(w / 2);
        int y = -(h / 2);

        g2d.fillRect(x, y, w, h);
    }

    @Override
    public void renderBounds(Graphics2D g2d) {
        int w = (int)size.getX();
        int h = (int)size.getY();
        int x = -(w / 2);
        int y = -(h / 2);

        g2d.drawRect(x, y, w, h);
    }

    @Override
    public void renderImage(Graphics2D g2d, BufferedImage image) {
        int w = (int)size.getX();
        int h = (int)size.getY();
        int x = -(w / 2);
        int y = -(h / 2);

        g2d.drawImage(image, x, y, w, h, null);
    }
}
