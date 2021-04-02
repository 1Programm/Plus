package com.programm.plus.engine.tests.bricks;

import com.programm.plus.core.components.SimpleRectangleDrawer;
import com.programm.plus.core.components.material.ColorMaterial;
import com.programm.plus.core.obj.ObjectInstance;
import com.programm.plus.tags.Tag;

import java.awt.*;

public class Panel extends ObjectInstance {

    public Panel(float speed) {
        this.tag(BricksMain.PANEL);
        this.tag(Tag.ENTITY_NO_COLLISION_RESOLVING);
        this.addComponent(new PanelComponent(speed));
        this.addComponent(new ColorMaterial(Color.GRAY, Color.BLACK));
        this.addComponent(new SimpleRectangleDrawer());
    }

}
