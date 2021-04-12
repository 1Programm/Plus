package com.programm.projects.plus.renderer.swing.components;

import com.programm.projects.plus.maths.Vector1f;
import com.programm.projects.plus.renderer.api.components.ShapeModel;

import java.awt.*;

public class SwingCircleModel implements ISwingRenderModel{

    private final Vector1f radius;

    public SwingCircleModel(ShapeModel model) {
        this.radius = model.getRadius();
    }

    @Override
    public void renderFill(Graphics2D g2d) {
        int w, h; w = h = (int)(radius.getVal() * 2);
        int x, y; x = y = (int)(-radius.getVal());

        g2d.fillOval(x, y, w, h);
    }

    @Override
    public void renderBounds(Graphics2D g2d) {
        int w = (int)(radius.getVal() * 2);
        int h = (int)(radius.getVal() * 2);
        int x = (int)(-radius.getVal());
        int y = (int)(-radius.getVal());

        g2d.drawOval(x, y, w, h);
    }
}
