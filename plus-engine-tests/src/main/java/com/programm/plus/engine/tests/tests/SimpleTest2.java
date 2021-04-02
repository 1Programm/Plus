package com.programm.plus.engine.tests.tests;

import com.programm.plus.camera.target.TargetCamera;
import com.programm.plus.collision.api.Collider;
import com.programm.plus.core.component.Mover;
import com.programm.plus.core.components.KeyboardMover;
import com.programm.plus.core.components.SimpleRectangleDrawer;
import com.programm.plus.core.components.material.ColorMaterial;
import com.programm.plus.engine.api.GameEngine;
import com.programm.plus.engine.simple.SimpleEngine;
import com.programm.plus.goh.api.GameObjectHandler;

import java.awt.*;

public class SimpleTest2 {

    public static void main(String[] args) {
        GameEngine engine = new SimpleEngine()
                .setCamera(new TargetCamera(90))
                .enableCollision();

        engine.init("Simple Test 2", 600, 500);

        GameObjectHandler goh = engine.getContext().getObjectHandler();

        goh.createEmpty()
                .setPosition(100, 100)
                .setSize(32, 32)
                .addComponent(new ColorMaterial(Color.RED, Color.BLACK))
                .addComponent(new SimpleRectangleDrawer())
                .addComponent(new Mover())
                .addComponent(new KeyboardMover(100))
                .addTag(TargetCamera.TARGET)
                .addComponent(new Collider())
        .build();

        goh.createEmpty()
                .setPosition(200, 100)
                .setSize(50, 20)
                .addComponent(new ColorMaterial(Color.GRAY, Color.BLACK))
                .addComponent(new SimpleRectangleDrawer())
                .addComponent(new Collider())
        .build();

        engine.start();
    }

}
