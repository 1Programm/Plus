package com.programm.projects.plus.renderer.swing.components;

import lombok.Getter;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SwingPolygonModel implements ISwingRenderModel {

    @Getter
    private final Polygon polygon;

    public SwingPolygonModel(int[] xPoints, int[] yPoints, int length) {
        polygon = new Polygon(xPoints, yPoints, length);
    }

    @Override
    public void renderFill(Graphics2D g2d) {
        g2d.fillPolygon(polygon);
    }

    @Override
    public void renderBounds(Graphics2D g2d) {
        g2d.drawPolygon(polygon);
    }

    @Override
    public void renderImage(Graphics2D g2d, BufferedImage image) {
        //TODO
    }
}
