package com.programm.plus.engine.tests.tests;

import com.programm.plus.core.components.KeyboardMover;
import com.programm.plus.core.components.SimpleRectangleDrawer;
import com.programm.plus.core.components.material.ColorMaterial;
import com.programm.plus.engine.simple.SimpleEngine;
import com.programm.plus.goh.api.GameObjectHandler;
import com.programm.plus.tags.Tag;
import com.programm.plus.tags.TagSystem;

import java.awt.*;

public class TagsTest {

    public static final Tag ENTITY_DRAWN = new Tag("Drawn Entity", Tag.ENTITY_MOVABLE)
            .addRequiredComponent(SimpleRectangleDrawer.class)
            .addRequiredComponent(ColorMaterial.class, Color.BLUE, Color.BLACK);

    public static final Tag ENTITY_PLAYER = new Tag("Player", ENTITY_DRAWN)
            .addRequiredComponent(KeyboardMover.class, 100f);

    public static void main(String[] args) {
        SimpleEngine engine = new SimpleEngine();

        engine.enableCollision();
        engine.init("Tags Test", 600, 500);

        engine.getTagSystem().setTagInitMode(TagSystem.MODE_REQUIRED_FORCE_CREATE);

        GameObjectHandler goh = engine.getContext().getObjectHandler();

        goh.createEmpty()
                .setPosition(200, 200)
                .setSize(32, 32)
                .addTag(ENTITY_PLAYER)
        .build();

        goh.createEmpty()
                .setPosition(300, 400)
                .setSize(20, 40)
                .addTag(ENTITY_DRAWN)
                .addTag(Tag.OBJECT_IMMOVABLE)
        .build();

        engine.start();
    }

}
