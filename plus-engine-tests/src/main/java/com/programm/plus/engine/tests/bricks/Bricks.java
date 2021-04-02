package com.programm.plus.engine.tests.bricks;

import com.programm.plus.collision.api.Collider;
import com.programm.plus.core.components.SimpleRectangleDrawer;
import com.programm.plus.core.components.material.ColorMaterial;
import com.programm.plus.core.obj.ObjectInstance;

import java.awt.*;

public class Bricks extends ObjectInstance {

    public static final Color[] colorLevels = {
            new Color(245, 245, 245),
            new Color(220, 220, 220),
            new Color(200, 200, 200),
            Color.GREEN,
            Color.RED,
            Color.BLACK
    };

    public Bricks() {
        tag(BricksMain.BRICK);
        addComponent(new Collider());
        addComponent(new SimpleRectangleDrawer());
        addComponent(new ColorMaterial(Color.GRAY, Color.BLACK));
    }
}
