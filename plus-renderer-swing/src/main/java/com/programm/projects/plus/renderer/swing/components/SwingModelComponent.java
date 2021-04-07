package com.programm.projects.plus.renderer.swing.components;

import com.programm.projects.plus.renderer.api.components.IModelComponent;
import lombok.Getter;

import java.awt.*;

public class SwingModelComponent implements IModelComponent {

    @Getter
    private final Shape shape;

    public SwingModelComponent(Shape shape) {
        this.shape = shape;
    }

}
