package com.programm.plus.engine.tests.tests;

import com.programm.plus.camera.target.TargetCamera;
import com.programm.plus.collision.api.Collider;
import com.programm.plus.collision.api.CollisionListener;
import com.programm.plus.core.GameContext;
import com.programm.plus.core.component.Mover;
import com.programm.plus.core.component.UpdateComponent;
import com.programm.plus.core.components.KeyboardMover;
import com.programm.plus.core.components.SimpleRectangleDrawer;
import com.programm.plus.core.components.TimerAction;
import com.programm.plus.core.components.material.ColorMaterial;
import com.programm.plus.core.components.material.Material;
import com.programm.plus.core.obj.GameObject;
import com.programm.plus.engine.api.GameEngine;
import com.programm.plus.engine.simple.SimpleEngine;
import com.programm.plus.engine.simple.SimpleFpsLoop;
import com.programm.plus.goh.api.GameObjectHandler;
import com.programm.plus.tags.Tag;
import com.programm.plus.tags.TagSystem;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Platformer {

    private static final Tag ENVIRONMENT = new Tag("environment")
            .addRequiredComponent(Collider.class)
            .addRequiredComponent(SimpleRectangleDrawer.class);

    private static final Tag PLATFORM = new Tag("wall", ENVIRONMENT)
            .addRequiredComponent(ColorMaterial.class, Color.BLACK);

    private static final Tag ENTITY = new Tag("entity")
            .addRequiredComponent(Mover.class)
            .addRequiredComponent(Collider.class)
            .addRequiredComponent(SimpleRectangleDrawer.class);

    private static final Tag PLAYER = new Tag("player", ENTITY)
            .addRequiredComponent(KeyboardMover.class, 150.0f);

    public static void main(String[] args) {
        GameEngine engine = new SimpleEngine()
                .enableCollision()
                .setCamera(new TargetCamera(100))
                .setLoop(new SimpleFpsLoop(60));

        engine.init("Platformer", 600, 400);
        engine.getTagSystem().setTagInitMode(TagSystem.MODE_REQUIRED_FORCE_CREATE);

        GameObjectHandler goh = engine.getContext().getObjectHandler();

        goh.createEmpty()
                .setPosition(100, 100)
                .setSize(32, 32)
                .addComponent(new ColorMaterial(Color.BLUE, Color.BLACK))
                .addComponent(new PlayerBehavior())
                .addComponent(new Collider(new PlayerCollider()))
                .addComponent(new KeyboardMover(100, KeyEvent.VK_ESCAPE, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D))
                .addTag(PLAYER)
                .addTag(TargetCamera.TARGET)
        .build();

        goh.createEmpty()
                .addComponent(TimerAction.Repeat(190, () -> spawnNewPlatform(goh)))
        .build();

        spawnNewPlatform(goh);

        engine.start();
    }

    private static float curX = 0;

    private static void spawnNewPlatform(GameObjectHandler goh){
        float startY = 200;
        float yRange = 100;
        float xRange = 100;
        float w_min = 50;
        float w_max = 300;
        float h_min = 5;
        float h_max = 20;


        float rX = curX + (float)(Math.random() * xRange);
        float rY = startY + (float)(Math.random() * yRange);

        float rW = w_min + (float)(Math.random() * (w_max - w_min));
        float rH = h_min + (float)(Math.random() * (h_max - h_min));

        curX = rX + rW + 10; //New x pos

        goh.createEmpty()
                .setPosition(rX, rY)
                .setSize(rW, rH)
                .addTag(PLATFORM)
                .addComponent(new PlatformVanish(200))
        .build();

    }

    private static class PlatformVanish extends UpdateComponent {

        private float gTimer;
        private float timer;

        public PlatformVanish(int time){
            this.gTimer = time;
            this.timer = time;
        }

        @Override
        public void onUpdate(GameContext gameContext) {
            float delta = timer / gTimer;

            ColorMaterial material = getComponent(ColorMaterial.class);

            int val = (int)((1 - delta) * 255);
            Color color = new Color(val, val, val);
            material.setBgColor(color);

            timer--;

            if(timer < 0){
                die();
            }
        }
    }

    private static class PlayerBehavior extends UpdateComponent {

        private static final float ACC = 300f;
        private static final float MAX_VEL = 300f;

        private float velocityDown = 0;
        private boolean onGround;

        @Override
        public void onUpdate(GameContext context) {
            velocityDown += ACC * context.getDeltaTime();

            if(velocityDown > MAX_VEL){
                velocityDown = MAX_VEL;
            }

            if(onGround){
                if(context.getControlls().getKeyboard().isKeyPressed(KeyEvent.VK_W)){
                    velocityDown = -200;
                    onGround = false;
                }
            }

            if(transform.getPosition().getY() > 500){
                System.exit(1);
            }

            move(0, velocityDown * context.getDeltaTime());
        }

        void onGround(){
            this.velocityDown = 0;
            this.onGround = true;
        }

        void hitCeiling(){
            if(this.velocityDown < 0) {
                this.velocityDown = 0;
            }
        }

    }

    private static class PlayerCollider implements CollisionListener {

        @Override
        public void onCollision(GameObject player, GameObject other) {
            if(other.hasTag(ENVIRONMENT)){
                if((player.getTransform().getPosition().getY() + player.getTransform().getSize().getY()-10) < other.getTransform().getPosition().getY()) {
                    PlayerBehavior behavior = player.getComponents().get(PlayerBehavior.class);
                    behavior.onGround();
                }
                else if ((other.getTransform().getPosition().getY() + other.getTransform().getSize().getY() - 10) < player.getTransform().getPosition().getY()) {
                    PlayerBehavior behavior = player.getComponents().get(PlayerBehavior.class);
                    behavior.hitCeiling();
                }
            }
        }
    }

}
