package com.programm.projects.core.components;

import com.programm.projects.core.IComponent;
import lombok.Getter;

import java.awt.*;

public class ColorMaterial implements IComponent {

    @Getter
    private final Color color;

    public ColorMaterial(Color color) {
        this.color = color;
    }

    public ColorMaterial(int r, int g, int b){
        this(new Color(r, g, b));
    }
}
