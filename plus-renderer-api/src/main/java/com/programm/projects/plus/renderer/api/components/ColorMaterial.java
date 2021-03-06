package com.programm.projects.plus.renderer.api.components;

import lombok.Getter;

import java.awt.*;

@Getter
public class ColorMaterial implements Material {

    private final Color borderColor;
    private final Color fillColor;

    public ColorMaterial(Color borderColor){
        this(borderColor, null);
    }

    public ColorMaterial(Color borderColor, Color fillColor) {
        this.borderColor = borderColor;
        this.fillColor = fillColor;
    }
}
