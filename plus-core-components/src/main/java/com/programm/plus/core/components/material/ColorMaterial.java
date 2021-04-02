package com.programm.plus.core.components.material;

import com.programm.plus.core.component.Component;
import com.programm.plus.render.api.Pencil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@AllArgsConstructor
@Setter
@Getter
public class ColorMaterial extends Material {

    private Color bgColor;
    private Color fgColor;

    public ColorMaterial(Color bgColor) {
        this.bgColor = bgColor;
    }

    @Override
    public boolean prepareBackground(Pencil pencil) {
        if(bgColor != null) {
            pencil.setPaintMode(Pencil.PAINT_MODE_FILL);
            pencil.setColor(bgColor);
            return true;
        }

        return false;
    }

    @Override
    public boolean prepareForeground(Pencil pencil) {
        if(fgColor != null){
            pencil.setPaintMode(Pencil.PAINT_MODE_OUTLINE);
            pencil.setColor(fgColor);
            return true;
        }

        return false;
    }

    @Override
    public Component copy() {
        return new ColorMaterial(bgColor, fgColor);
    }
}
