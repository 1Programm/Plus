package com.programm.projects.plus.renderer.swing.components;

import com.programm.projects.core.IComponent;
import lombok.Getter;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public class SwingShape implements IComponent {

    public static SwingShape Rectangle(int width, int height){
        int x = - width / 2;
        int y = - height / 2;
        int w = width / 2 + ((width % 2 == 0) ? 0 : 1);
        int h = height / 2 + ((height % 2 == 0) ? 0 : 1);

        return new SwingShape(new Rectangle2D.Float(x, y, w, h));
    }

    @Getter
    private final Shape shape;

    public SwingShape(Shape shape) {
        this.shape = shape;
    }

}
