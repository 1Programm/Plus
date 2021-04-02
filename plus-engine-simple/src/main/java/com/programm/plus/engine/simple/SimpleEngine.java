package com.programm.plus.engine.simple;

import com.programm.plus.camera.api.Camera;
import com.programm.plus.camera.stat.StaticCamera;
import com.programm.plus.collision.aabb.AABBCollisionSystem;
import com.programm.plus.collision.api.CollisionInfo;
import com.programm.plus.collision.api.CollisionSystem;
import com.programm.plus.engine.api.GameEngine;
import com.programm.plus.engine.api.GameLoop;
import com.programm.plus.goh.api.GameObjectHandler;
import com.programm.plus.goh.simple.SimpleGOH;
import com.programm.plus.maths.Vector2f;
import com.programm.plus.render.api.Renderer;
import com.programm.plus.render.api.controlls.KeyboardControll;
import com.programm.plus.render.api.controlls.MouseControll;
import com.programm.plus.render.jcanvas.JCanvasRenderer;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Slf4j
public class SimpleEngine extends GameEngine {

    private GameLoop loop;
    private Renderer renderer;
    private Color clearColor;
    private GameObjectHandler goh;

    private boolean collision;
    private CollisionSystem collisionSystem;

    private Camera camera;
    private Vector2f camPos;

    @Override
    protected void update() {
        if(clearColor != null) {
            getContext().getPencil().clearWindow(clearColor);
        }

        super.update();
    }

    @Override
    protected GameLoop initLoop() {
        if(loop == null){
            log.debug("No GameLoop specified. Default GameLoop: {}", SimpleLoop.class.getName());
            this.loop = new SimpleLoop();
        }
        else {
            log.debug("GameLoop: {}", loop.getClass().getName());
        }

        return loop;
    }

    @Override
    protected Renderer initRenderer() {
        if(renderer == null) {
            log.debug("No Renderer specified. Default Renderer: {}", JCanvasRenderer.class.getName());
            this.renderer = new JCanvasRenderer(2);
        }
        else {
            log.debug("Renderer: {}", renderer.getClass().getName());
        }

        if(clearColor == null) {
            log.debug("No Clear Color specified. Default Clear Color: {}", Color.WHITE);
            this.clearColor = Color.WHITE;
        }
        else {
            log.debug("Clear Color: {}", loop.getClass().getName());
        }

        return renderer;
    }

    @Override
    protected GameObjectHandler initGoh() {
        if(goh == null){
            log.debug("No GOH specified. Default GOH: {}", SimpleGOH.class.getName());
            this.goh = new SimpleGOH();
        }
        else {
            log.debug("GOH: {}", goh.getClass().getName());
        }

        return goh;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected <T extends CollisionInfo> CollisionSystem<T> initCollisionSystem() {
        if(collisionSystem == null) {
            if (!collision) {
                log.debug("No Collision System specified -> Collision disabled!");
                return null;
            }
            else {
                log.debug("No Collision System specified. Default Collision System: {}", AABBCollisionSystem.class);
                this.collisionSystem = new AABBCollisionSystem();
            }
        }

        return (CollisionSystem<T>) collisionSystem;
    }

    @Override
    protected Camera initCamera(int width, int height) {
        if(camera == null){
            log.debug("No Camera specified. Default Camera: {}", StaticCamera.class);

            if(camPos == null){
                this.camPos = new Vector2f(width/2, height/2);
                log.debug("No Camera Position specified. Default Position: {}", camPos);
            }

            this.camera = new StaticCamera(camPos);
        }
        else {
            if(camera.getPosition() == null){
                if(camPos == null){
                    this.camPos = new Vector2f(width/2, height/2);
                    log.debug("No Camera Position specified. Default Position: {}", camPos);
                }

                camera.setPosition(camPos);
            }

            log.debug("Camera: {}", camera.getClass().getName());
        }

        return camera;
    }

    public SimpleEngine enableCollision(){
        this.collision = true;
        return this;
    }

    public SimpleEngine enableCollision(CollisionSystem collisionSystem){
        this.collisionSystem = collisionSystem;
        return this;
    }

    @Override
    public SimpleEngine enableRotation() {
        super.enableRotation();
        return this;
    }

    public SimpleEngine setKeyboard(KeyboardControll keyboardControll){
        this.getWindow()
                .getControlls()
                .setKeyboard(keyboardControll);
        return this;
    }

    public SimpleEngine setMouse(MouseControll mouseControll){
        this.getWindow()
                .getControlls()
                .setMouse(mouseControll);
        return this;
    }

    public SimpleEngine setLoop(GameLoop loop) {
        this.loop = loop;
        return this;
    }

    public SimpleEngine setRenderer(Renderer renderer) {
        this.renderer = renderer;
        return this;
    }

    public SimpleEngine setClearColor(Color clearColor) {
        this.clearColor = clearColor;
        return this;
    }

    public SimpleEngine setGoh(GameObjectHandler goh) {
        this.goh = goh;
        return this;
    }

    public SimpleEngine setCamera(Camera camera){
        this.camera = camera;
        return this;
    }

    public SimpleEngine setCameraPosition(Vector2f cameraPosition){
        this.camPos = cameraPosition;
        return this;
    }
}
