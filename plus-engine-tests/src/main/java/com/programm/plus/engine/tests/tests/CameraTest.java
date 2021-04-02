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

public class CameraTest {

    public static void main(String[] args) {
        GameEngine engine = new SimpleEngine()
                .setCamera(new TargetCamera(100))
                .enableCollision();

        engine.init("Camera Test", 600, 500);

        GameObjectHandler goh = engine.getContext().getObjectHandler();

        goh.createEmpty()
                .setPosition(284, 0)
                .setSize(32, 32)
                .addComponent(new ColorMaterial(Color.BLUE, Color.BLACK))
                .addComponent(new SimpleRectangleDrawer())
                .addComponent(new Mover())
                .addComponent(new KeyboardMover(120))
                .addComponent(new Collider())
                .addTag(TargetCamera.TARGET)
        .build();

        goh.createEmpty()
                .setPosition(50, 100)
                .setSize(20, 20)
                .addComponent(new ColorMaterial(Color.RED, Color.BLACK))
                .addComponent(new SimpleRectangleDrawer())
                .addComponent(new Collider())
                .addComponent(new Mover())
                .addTag(TargetCamera.TARGET)
        .build();

        goh.createEmpty()
                .setPosition(300, 250)
                .setSize(16, 16)
                .addComponent(new ColorMaterial(Color.GRAY, Color.BLACK))
                .addComponent(new SimpleRectangleDrawer())
        .build();

        engine.start();
    }

}
