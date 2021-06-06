package com.programm.projects.plus.renderer.swing.components;

import com.programm.projects.plus.maths.Vector2f;
import com.programm.projects.plus.renderer.api.components.ShapeModel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SwingLineModel implements ISwingRenderModel{

    private final Vector2f l1, l2;

    public SwingLineModel(ShapeModel model){
        l1 = model.getL1();
        l2 = model.getL2();
    }

    @Override
    public void renderFill(Graphics2D g2d) {
        g2d.drawLine((int)l1.getX(), (int)l1.getY(), (int)l2.getX(), (int)l2.getY());
    }

    @Override
    public void renderBounds(Graphics2D g2d) {
        g2d.drawLine((int)l1.getX(), (int)l1.getY(), (int)l2.getX(), (int)l2.getY());
    }

    @Override
    public void renderImage(Graphics2D g2d, BufferedImage image) {
        //TODO
    }
}
