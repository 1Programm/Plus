package com.programm.plus.engine.tests.bricks;

import com.programm.plus.core.components.SimpleVelocityMover;
import com.programm.plus.engine.simple.SimpleEngine;
import com.programm.plus.engine.simple.SimpleLoop;
import com.programm.plus.goh.api.GameObjectHandler;
import com.programm.plus.maths.Vector2f;
import com.programm.plus.render.api.GameWindow;
import com.programm.plus.tags.Tag;
import com.programm.plus.tags.TagSystem;

import java.awt.event.KeyEvent;

public class BricksMain {

    public static final Tag PANEL = new Tag("Panel", Tag.ENTITY_MOVABLE);
    public static final Tag BRICK = new Tag("Brick", Tag.OBJECT_IMMOVABLE);
    public static final Tag BALL = new Tag("Ball", Tag.ENTITY_MOVABLE);

    public static void main(String[] args) {

        SimpleEngine engine = new SimpleEngine();

        engine.setLoop(new SimpleLoop()
                .stopOn(() -> engine
                        .getContext()
                        .getControlls()
                        .getKeyboard()
                        .isKeyPressed(KeyEvent.VK_ESCAPE)
                )
        );

        engine.enableCollision();


        engine.init("Bricks", 600, 500);
        engine.getTagSystem().setTagInitMode(TagSystem.MODE_REQUIRED_WARN_FORCE_CREATE);


        GameObjectHandler goh = engine.getContext().getObjectHandler();
        GameWindow window = engine.getWindow();

        createBricks(goh, window, 8, 4, 100);

        Panel panel = new Panel(300);
        goh.create(panel)
                .setPosition(10, window.getHeight() - 50)
                .setSize(100, 20)
        .build();

        Ball ball = new Ball();
        goh.create(ball)
                .setPosition(window.getWidth()/2 - 5, window.getHeight()/2 - 5)
                .setSize(10, 10)
                .addComponent(new SimpleVelocityMover(new Vector2f(0, 100)))
        .build();


        engine.start();
    }

    public static void createBricks(GameObjectHandler goh, GameWindow window, int numBricksX, int numBricksY, float maxWidth){
        Bricks bricks = new Bricks();

        float offsetX = 10;
        float offsetY = 10;

        float width = (window.getWidth() - (numBricksX + 1) * offsetX) / numBricksX;
        float height = 20;

        for(int x=0;x<numBricksX;x++){
            for(int y=0;y<numBricksY;y++){
                goh.create(bricks)
                        .setPosition(offsetX + x * (width + offsetX), offsetY + y * (height + offsetY))
                        .setSize(width, height)
                .build();
            }
        }
    }

}
