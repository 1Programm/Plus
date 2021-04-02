package com.programm.plus.engine.tests.bricks;

import com.programm.plus.core.component.Component;
import com.programm.plus.core.component.ExtraComponent;
import com.programm.plus.core.components.material.ColorMaterial;

public class BrickComponent extends ExtraComponent {

    private int level;

    public BrickComponent(int level) {
        this.level = level;
    }

    public void decreaseLevel(){
        this.level--;

        if(level < 0){
            die();
        }
        else {

            ColorMaterial cMat = getComponent(ColorMaterial.class);

            if(cMat == null) return;

            cMat.setBgColor(Bricks.colorLevels[level]);
        }
    }

    @Override
    public Component copy() {
        return new BrickComponent(level);
    }
}
