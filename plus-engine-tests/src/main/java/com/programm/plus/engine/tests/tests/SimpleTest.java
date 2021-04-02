package com.programm.plus.engine.tests.tests;

import com.programm.plus.core.component.Mover;
import com.programm.plus.core.components.KeyboardMover;
import com.programm.plus.core.components.SimpleRectangleDrawer;
import com.programm.plus.core.components.material.ColorMaterial;
import com.programm.plus.engine.api.GameEngine;
import com.programm.plus.engine.simple.SimpleEngine;
import com.programm.plus.goh.api.GameObjectHandler;

import java.awt.*;

public class SimpleTest {

    public static void main(String[] args) {
        GameEngine engine = new SimpleEngine();

        engine.init("Simple Test", 600, 500);

        GameObjectHandler goh = engine.getContext().getObjectHandler();

        goh.createEmpty()
                .setPosition(0, 0)
                .setSize(32, 32)
                .addComponent(new Mover())
                .addComponent(new KeyboardMover(100))
                .addComponent(new ColorMaterial(Color.GREEN, Color.BLACK))
                .addComponent(new SimpleRectangleDrawer())
        .build();

        engine.start();
    }

}
