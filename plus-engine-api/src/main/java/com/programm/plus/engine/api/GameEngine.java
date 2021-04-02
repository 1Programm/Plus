package com.programm.plus.engine.api;

import com.programm.plus.camera.api.Camera;
import com.programm.plus.collision.api.CollisionInfo;
import com.programm.plus.collision.api.CollisionSystem;
import com.programm.plus.collision.api.CollisionTester;
import com.programm.plus.core.GameContext;
import com.programm.plus.engine.api.settings.EngineSettings;
import com.programm.plus.engine.api.settings.EngineSettingsBuilder;
import com.programm.plus.goh.api.GameObjectHandler;
import com.programm.plus.render.api.GameWindow;
import com.programm.plus.render.api.Pencil;
import com.programm.plus.render.api.Renderer;
import com.programm.plus.tags.TagSystem;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class GameEngine {

    private GameLoop loop;
    private Renderer renderer;
    private GameObjectHandler goh;
    @Getter private TagSystem tagSystem;
    @Getter private CollisionSystem<? extends CollisionInfo> collisionSystem;
    @Setter private CollisionTester collisionConsumer;

    @Getter private GameWindow window;
    @Getter private GameContext context;

    private EngineSettingsBuilder engineSettingsBuilder = new EngineSettingsBuilder();

    public final void init(String title, int width, int height){
        if(log.isDebugEnabled()) {
            log.info("Initializing engine:");
            log.info("- - - - - - - - - - - - - - - - - - - -");
        }
        else {
            log.info("Initializing engine [...]");
        }

        long start = System.currentTimeMillis();

        this.loop = initLoop();
        this.renderer = initRenderer();
        this.goh = initGoh();
        this.tagSystem = new TagSystem();
        this.collisionSystem = initCollisionSystem();
        Camera camera = initCamera(width, height);

        this.window = this.renderer.initWindow(title, width, height);
        Pencil pencil = this.renderer.initPencil();

        pencil.init(window, camera);

        this.loop.init(this::init, this::preUpdate, this::onLoopStop, window::shouldClose);

        if(this.collisionSystem != null) {
            this.collisionSystem.init();
            this.collisionConsumer = collisionSystem::runAndResolve;
        }

        EngineSettings engineSettings = engineSettingsBuilder.build();

        this.context = new GameContext(window, this.loop::getDeltaTime, pencil, goh, engineSettings);

        this.goh.init(context, tagSystem, camera);

        if(log.isDebugEnabled()){
            log.info("- - - - - - - - - - - - - - - - - - - -");
        }
        
        long stop = System.currentTimeMillis();
        log.info("Initialisation took {} ms.", stop - start);
    }

    protected abstract GameLoop initLoop();
    protected abstract Renderer initRenderer();
    protected abstract GameObjectHandler initGoh();
    protected abstract <T extends CollisionInfo> CollisionSystem<T> initCollisionSystem();
    protected abstract Camera initCamera(int width, int height);

    protected void init() {}

    public void start(){
        log.info("Starting engine ...");
        window.setVisible(true);
        loop.start();
    }

    public void stop(){
        log.info("Stopping engine ...");
        window.setVisible(false);
        renderer.cleanUp();
        loop.stop();
    }

    private void preUpdate(){
        if(!window.isInitialized()){
            log.warn("Window is not yet initialized and cannot be rendered.");
            return;
        }

        renderer.preRender();
        update();
    }

    protected void update(){
        goh.update(collisionConsumer);
        renderer.postRender();
    }

    private void onLoopStop(){
        log.info("Engine stopped.");
        log.info("Cleaning up ...");
        renderer.cleanUp();
    }

    public GameEngine enableRotation(){
        engineSettingsBuilder.setRotationEnabled(true);
        return this;
    }

}
